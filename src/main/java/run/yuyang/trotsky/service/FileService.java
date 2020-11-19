package run.yuyang.trotsky.service;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;

@ApplicationScoped
public class FileService {

    @Inject
    Vertx vertx;

    public String getRelPath(String path) {
        if (null == path || path.equals("") || path.equals(".")) {
            return new File("").getAbsolutePath();
        } else {
            File file = new File(path);

            if (file.exists() && file.isDirectory()) {
                return file.getAbsolutePath();
            }
            if (file.mkdir()) {
                return file.getAbsolutePath();
            } else {
                return null;
            }
        }
    }

    public boolean copyStaticFile(String path) {
        String[] files = new String[]{
                "index.html", "README.md", "_coverpage.md", "notes.md", ".trotsky/user.json", ".trotsky/index.json", ".trotsky/note.json", "img/logo.jpg"
        };
        String[] dirs = new String[]{
                "img", ".trotsky", "notes"
        };
        for (String dir : dirs) {
            File file = new File(path + "/" + dir);
            if (!(file.exists() && file.isDirectory())) {
                if (!file.mkdir()) {
                    System.out.println("失败❌ " + file.getAbsolutePath());
                    return false;
                }
                System.out.println("创建✅ " + file.getAbsolutePath());
            }
        }
        for (String fileName : files) {
            File file = new File(path + "/" + fileName);
            if (!file.exists()) {
                System.out.println("创建✅ " + file.getAbsolutePath());
                vertx.fileSystem()
                        .copyBlocking("static/" + fileName, path + "/" + fileName);
            } else {
                System.out.println("已存在✅ " + file.getAbsolutePath());
            }

        }
        return true;
    }

    public void saveNewFile(String path, String text) {
        vertx.fileSystem().createFile(path, res -> {
            if (res.succeeded()){
                vertx.fileSystem().writeFile(path, Buffer.buffer(text), respose -> {
                    if (respose.succeeded()){
                    }
                });
            }else {
                System.out.println(res.cause());
            }
        });
    }

}
