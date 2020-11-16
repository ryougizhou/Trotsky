package run.yuyang.trotsky.service;

import io.vertx.core.Vertx;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.*;

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
                "index.html", "README.md", "_coverpage.md", "notes.md", ".trotsky/user.json", ".trotsky/index.json", "img/logo.jpg"
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
                vertx.fileSystem()
                        .copy("/static/" + fileName, path + "/" + fileName, res -> {
                            if (res.succeeded()) {
                                System.out.println("创建✅ " + file.getAbsolutePath());
                            } else {
                                System.out.println("创建❌ " + file.getAbsolutePath());
                            }
                        });
            } else {
                System.out.println("已存在✅ " + file.getAbsolutePath());
            }

        }
        return true;
    }

}