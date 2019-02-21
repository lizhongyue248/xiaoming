package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.model.entity.SysLog;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * @date 2019-02-19 20:27
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysLogServiceTest {
    @Autowired
    private SysLogService sysLogService;

    @Test
    public void getByIdTest() {
        SysLog sysLog = sysLogService.getById(1L);
        assertNotNull(sysLog);
        log.info("id: {} is {}", sysLog.getId(), sysLog);
    }

    @Test
    public void listTest() {
        List<SysLog> sysLogs = sysLogService.list();
        assertNotNull(sysLogs);
        sysLogs.forEach(sysLog -> log.info("id: {} is {}", sysLog.getId(), sysLog));
    }

    @Test
    public void getOneTest() {
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysLog::getId, 10L);
        SysLog one = sysLogService.getOne(queryWrapper);
        log.info(one.toString());
    }

    @Test
    public void pageTest(){
        IPage<SysLog> page = sysLogService.page(new Page<>(1, 10));
        assertNotNull(page);
        log.info("size: {}, pages: {}, now: {}", page.getSize(), page.getPages(), page.getCurrent());
        List<SysLog> sysLogs = page.getRecords();
        sysLogs.forEach(sysLog -> log.info("id: {} is {}", sysLog.getId(), sysLog));
    }

}