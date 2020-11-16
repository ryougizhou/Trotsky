package run.yuyang.trotsky.service;

import run.yuyang.trotsky.commom.exception.TrotskyException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InitService {


    @Inject
    FileService fileService;

    public void init(String path) throws TrotskyException {
        String relPath = fileService.getRelPath(path);
        if (null == relPath) {
            throw new TrotskyException("未找到该文件夹，或无法创建文件夹。");
        }
        System.out.println("项目路径：" + relPath);
        if (fileService.copyStaticFile(relPath)) {
            System.out.println("配置文件初始完毕");
        } else {
            throw new TrotskyException("配置文件初始失败。");
        }
    }


}
