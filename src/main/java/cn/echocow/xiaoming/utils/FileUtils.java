package cn.echocow.xiaoming.utils;

import cn.echocow.xiaoming.model.entity.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * 文件工具类
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-11 12:12
 */
@Slf4j
@Component
public class FileUtils {

    private static final Integer COUNT = 3;
    /**
     * 删除本地文件
     *
     * @param file 文件信息
     */
    @Async
    public void localFileDelete(File file) {
        java.io.File localFile = new java.io.File(file.getDirName(), file.getName());
        if (localFile.exists()) {
            int count = 0;
            // 删除重试
            while (count < COUNT) {
                if (localFile.delete()) {
                    break;
                }
                count++;
                log.warn("delete file " + file.getName() + " failed.");
            }
        }
    }

    /**
     * 删除本地多个文件
     *
     * @param files 删除的文件
     */
    @Async
    public void localFilesDelete(Collection<File> files){
        if (files.size() < 1){
            return;
        }
        for (File file : files) {
            localFileDelete(file);
        }
    }
}
