package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.model.entity.File;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-20 22:22
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Test
    public void findAllByTaskTest() {
        List<File> files = fileService.findAllByTask(1L);
        log.info(files.toString());
    }

    @Test
    public void deleteFilesTest() {
        File file = fileService.getById(1L);
        List<File> files = new ArrayList<>();
        files.add(file);
        fileService.deleteFiles(files);
    }

    @Test
    public void findByNameTest() {
//        File file = fileService.findByStudentIdAndTaskId("1");
//        assertNotNull(file);
//        log.info(file.toString());
    }
}