package cn.echocow.xiaoming.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-11 23:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailUtilsTest {

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private Configuration configuration;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void sendSimpleMail() throws InterruptedException {
        mailUtils.sendSimpleMail("echocow@qq.com", "lizhongyue248@163.com",
                "测试主题", "测试内容");
        Thread.sleep(500000);
    }

    @Test
    public void sendFreeMarkerTipMail() throws Exception {
        Template template = configuration.getTemplate("tipMailTemplate.ftl");
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, null);
        mailUtils.sendTipMail("echocow@qq.com", "lizhongyue248@163.com",
                "tip", content);
        Thread.sleep(50000);
    }

    @Test
    public void sendTipMail() throws Exception {
        Context context = new Context();
        context.setVariable("username","xiaoming");
        String content = templateEngine.process("tipMailTemplate.html", context);
        mailUtils.sendTipMail("echocow@qq.com", "lizhongyue248@163.com",
                "tip", content);
        Thread.sleep(50000);
    }
}