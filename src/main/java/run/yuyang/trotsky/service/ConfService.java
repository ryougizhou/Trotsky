package run.yuyang.trotsky.service;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import run.yuyang.trotsky.model.conf.IndexConf;
import run.yuyang.trotsky.model.conf.UserConf;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * @author YuYang
 */
@ApplicationScoped
public class ConfService {

    private static final Logger logger = Logger.getLogger("ConfService.class");

    @Inject
    Vertx vertx;

    private IndexConf indexConf;

    private UserConf userConf;

    private String workerPath;

    public ConfService() {
        indexConf = new IndexConf();
        userConf = new UserConf();
    }

    public void readConfFromFile(String path) {
        workerPath = path;
        vertx.fileSystem().readFile(path + "/.trotsky/index.json", res -> {
            if (res.succeeded()) {
                JsonObject object = res.result().toJsonObject();
                indexConf.setVersion(object.getString("version"));
            }
        });

    }

    public IndexConf getIndexConf() {
        return indexConf;
    }

    public void setIndexConf(IndexConf indexConf) {
        this.indexConf = indexConf;
    }

    public UserConf getUserConf() {
        return userConf;
    }

    public void setUserConf(UserConf userConf) {
        this.userConf = userConf;
    }

    public String getWorkerPath() {
        return workerPath;
    }

}
