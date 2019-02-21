package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.model.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-09 01:20
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @Test
    public void getByIdTest() {
        Task task = taskService.getById(1L);
        assertNotNull(task);
        log.info("id: {} is {}", task.getId(), task);
    }

    @Test
    public void listTest() {
        List<Task> tasks = taskService.list();
        assertNotNull(tasks);
        tasks.forEach(task -> log.info("id: {} is {}", task.getId(), task));
    }

}