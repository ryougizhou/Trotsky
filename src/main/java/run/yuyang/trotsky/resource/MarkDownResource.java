package run.yuyang.trotsky.resource;

import io.smallrye.mutiny.Uni;
import run.yuyang.trotsky.commom.utils.ResUtils;
import run.yuyang.trotsky.model.conf.NoteConf;
import run.yuyang.trotsky.model.request.MDParam;
import run.yuyang.trotsky.service.AsyncFileService;
import run.yuyang.trotsky.service.ConfService;
import run.yuyang.trotsky.service.FileService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author YuYang
 */
@Path("/md")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MarkDownResource {

    @Inject
    ConfService confService;

    @Inject
    FileService fileService;

    @Inject
    AsyncFileService asyncFileService;

    @GET
    public Response getAllInfo() {
        return ResUtils.success(confService.getNoteConfs());
    }

    @GET
    @Path("/{name}/info")
    public Response getInfoByName(@PathParam("name") String name) {
        if (confService.existNoteConf(name)) {
            return ResUtils.success(confService.getNoteConf(name));
        } else {
            return ResUtils.failure("未找到该文件信息");
        }
    }

    @GET
    @Path("/{name}/async")
    public Uni<String> getTextAsync(@PathParam("name") String name) {
        if (confService.existNoteConf(name) && fileService.existFile(confService.getPath(name))) {
            return asyncFileService.getFileAsync(confService.getPath(name));
        }
        return null;
    }

    @GET
    @Path("/{name}/sync")
    public String getTextSync(@PathParam("name") String name) {
        if (confService.existNoteConf(name) && fileService.existFile(confService.getPath(name))) {
            return fileService.getFileSync(confService.getPath(name));
        }
        return null;
    }


    @POST
    public Response newFile(MDParam param) {
        if (confService.existNoteConf(param.getFather())  && confService.getNoteConf(param.getFather()).getIsDir()) {
            fileService.saveNewFile(confService.getRelPath(param), param.getText(), NoteConf.map(param, confService.getPath(param.getFather())));
            return ResUtils.success();
        } else {
            return ResUtils.failure();
        }
    }

    @DELETE
    @Path("/{name}")
    public Response delNotes(@PathParam("name") String name) {
        if (confService.existNoteConf(name)) {
            fileService.delFile(confService.getPath(name),name);
            return ResUtils.success();
        } else {
            return ResUtils.failure("未找到该文件信息");
        }
    }


}
