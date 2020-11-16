package run.yuyang.trotsky;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import run.yuyang.trotsky.commom.exception.TrotskyException;
import run.yuyang.trotsky.service.ConfService;
import run.yuyang.trotsky.service.FileService;
import run.yuyang.trotsky.service.InitService;

import javax.inject.Inject;


@QuarkusMain
public class Trotsky implements QuarkusApplication {


    private String banner = "_________ _______  _______ _________ _______  _                \n" +
            "\\__   __/(  ____ )(  ___  )\\__   __/(  ____ \\| \\    /\\|\\     /|\n" +
            "   ) (   | (    )|| (   ) |   ) (   | (    \\/|  \\  / /( \\   / )\n" +
            "   | |   | (____)|| |   | |   | |   | (_____ |  (_/ /  \\ (_) / \n" +
            "   | |   |     __)| |   | |   | |   (_____  )|   _ (    \\   /  \n" +
            "   | |   | (\\ (   | |   | |   | |         ) ||  ( \\ \\    ) (   \n" +
            "   | |   | ) \\ \\__| (___) |   | |   /\\____) ||  /  \\ \\   | |   \n" +
            "   )_(   |/   \\__/(_______)   )_(   \\_______)|_/    \\/   \\_/   \n" +
            "                                                               ";

    @Inject
    InitService initService;

    @Inject
    ConfService confService;

    @Inject
    FileService fileService;

    @ConfigProperty(name = "trotsky.version", defaultValue = "unkown")
    private String version;

    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
    private String port;

    @Override
    public int run(String... args) {
        if (args.length == 0) {
            return help();
        } else if (args.length == 1) {
            String cmd = args[0];
            if (cmd.equals("-v") || cmd.equals("--version")) {
                return version();
            } else if (cmd.equals("init")) {
                try {
                    initService.init("");
                } catch (TrotskyException e) {
                    System.out.println(e.getMessage());
                    return -1;
                }
            } else {
                return help();
            }
        } else if (args.length == 2) {
            if (args[0].equals("init")) {
                try {
                    initService.init(args[1]);
                    return 0;
                } catch (TrotskyException e) {
                    System.out.println(e.getMessage());
                    return -1;
                }
            } else if (args[0].equals("serve")) {
                confService.readConfFromFile(fileService.getRelPath(args[1]));
                System.out.println(banner);
                System.out.println("\n\n    trotsky is listening port : " + port + " \n    the URL is http://127.0.0.1:" + port);
                Quarkus.waitForExit();
            }
        } else {
            return help();
        }
        return help();
    }


    public int help() {
        System.out.println("例子：trotsky <init|serve> <path>");
        System.out.println("");
        System.out.println("常用命令:");
        System.out.println("\ttrotsky init [path]");
        System.out.println("\ttrotsky serve [path]");
        System.out.println("\ttrotsky start <path>");
        System.out.println("");
        System.out.println("其他命令:");
        System.out.println("\ttrotsky --help,-h");
        System.out.println("\ttrotsky --version,-v");
        return 0;
    }

    public int version() {
        System.out.println("\ntrotsky version : \n\t\t" + version + "\n");
        return 0;
    }

}
