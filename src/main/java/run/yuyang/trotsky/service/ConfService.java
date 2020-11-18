package run.yuyang.trotsky.service;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import run.yuyang.trotsky.model.conf.IndexConf;
import run.yuyang.trotsky.model.conf.UserConf;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

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

    private String workerPath;

    private String UUID;

    public void readConfFromFile(String path) {
        workerPath = path;
        JsonObject object = vertx.fileSystem().readFileBlocking(path + "/.trotsky/index.json").toJsonObject();
        indexConf = object.mapTo(IndexConf.class);
        object = vertx.fileSystem().readFileBlocking(path + "/.trotsky/user.json").toJsonObject();
        userConf = object.mapTo(UserConf.class);
    }

    public void saveUserConf(UserConf userConf) {
        vertx.fileSystem().writeFileBlocking(workerPath + "/.trotsky/user.json", Buffer.buffer(JsonObject.mapFrom(userConf).toString() + "\n"));
    }

}