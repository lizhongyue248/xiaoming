package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.resource.ApplicationResource;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * profile
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 22:36
 */
@RestController
public class MainController {

    @RequestMapping(value = {"/"}, produces = "application/json;charset=UTF-8")
    public HttpEntity<?> profile() {
        return ResponseEntity.ok(new Resource<>(new ApplicationResource()));
    }

}
