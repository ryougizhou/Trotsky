package run.yuyang.trotsky.model.conf;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

/**
 * @author YuYang
 */
@Data
@RegisterForReflection
public class NoteConf {

    private String name;

    private String father;

    private String path;

    private Boolean show;

    private Boolean isDir;

}
