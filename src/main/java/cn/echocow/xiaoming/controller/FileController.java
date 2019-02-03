package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.base.BaseController;
import cn.echocow.xiaoming.entity.File;
import cn.echocow.xiaoming.service.FileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 22:43
 */
@RestController
@RequestMapping("/files")
public class FileController extends BaseController<File, FileService> {

    @Override
    public Class getControllerClass() {
        return this.getClass();
    }

}
