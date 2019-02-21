package cn.echocow.xiaoming.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-21 18:17
 */
@Component
public class MetaHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object username = getUsernmae();
        this.setInsertFieldValByName("createUser", username, metaObject);
        this.setInsertFieldValByName("modifyUser", username, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setUpdateFieldValByName("modifyUser", getUsernmae(), metaObject);
    }

    private Object getUsernmae(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return Optional.ofNullable(authentication.getPrincipal()).orElse("");
        }
        return "";
    }
}
