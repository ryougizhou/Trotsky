package run.yuyang.trotsky.service;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import run.yuyang.trotsky.model.conf.IndexConf;
import run.yuyang.trotsky.model.conf.NoteConf;
import run.yuyang.trotsky.model.conf.UserConf;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YuYang
 */
@ApplicationScoped
@Setter
@Getter
public class ConfService {

    @Inject
    Vertx vertx;

    @ConfigProperty(name = "trotsky.version", defaultValue = "unkown")
    private String version;

    private IndexConf indexConf;

    private UserConf userConf;

    private Map<String, NoteConf> noteConfs;

    private String workerPath;

    private String UUID;

    public void readConfFromFile(String path) {
        workerPath = path;
        JsonObject object = vertx.fileSystem().readFileBlocking(path + "/.trotsky/index.json").toJsonObject();
        indexConf = object.mapTo(IndexConf.class);
        object = vertx.fileSystem().readFileBlocking(path + "/.trotsky/user.json").toJsonObject();
        userConf = object.mapTo(UserConf.class);
        JsonArray array = vertx.fileSystem().readFileBlocking(path + "/.trotsky/note.json").toJsonArray();
        noteConfs = new HashMap<>();
        array.forEach(obj -> {
            if (obj instanceof JsonObject) {
                NoteConf conf = ((JsonObject) obj).mapTo(NoteConf.class);
                noteConfs.put(conf.getName(), conf);
            }

        });
    }

    public void saveUserConf() {
        vertx.fileSystem().writeFileBlocking(workerPath + "/.trotsky/user.json", Buffer.buffer(JsonObject.mapFrom(userConf).toString() + "\n"));
    }

    public void saveUserConf(UserConf userConf) {
        vertx.fileSystem().writeFileBlocking(workerPath + "/.trotsky/user.json", Buffer.buffer(JsonObject.mapFrom(userConf).toString() + "\n"));
    }

}