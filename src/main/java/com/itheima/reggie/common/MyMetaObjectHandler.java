package com.itheima.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 原数据对象处理器
 */

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入操作自动填充
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
      log.info("公共字段填充[insert]");
      metaObject.setValue("createTime", LocalDateTime.now());
      metaObject.setValue("updateTime", LocalDateTime.now());
      metaObject.setValue("createUser", new Long(1));
      metaObject.setValue("updateUser", new Long(1));
//      log.info(metaObject.toString());
    }

    /**
     * 更新操作自动填充
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段填充[update]");
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", new Long(1));
        log.info(metaObject.toString());
    }
}
