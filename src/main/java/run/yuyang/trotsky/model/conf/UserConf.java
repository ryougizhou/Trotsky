package run.yuyang.trotsky.model.conf;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.ToString;

@Data
@RegisterForReflection
@ToString
public class UserConf {

    private String version;

    private String email;

    private String password;


}
