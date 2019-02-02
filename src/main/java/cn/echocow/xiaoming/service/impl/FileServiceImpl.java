package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.entity.File;
import cn.echocow.xiaoming.repository.FileRepository;
import cn.echocow.xiaoming.service.FileService;
import org.springframework.stereotype.Service;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
@Service
public class FileServiceImpl extends BaseServiceImpl<File, Long, FileRepository> implements FileService {
}
