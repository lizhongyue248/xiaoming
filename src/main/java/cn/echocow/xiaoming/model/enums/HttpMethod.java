package cn.echocow.xiaoming.model.enums;

/**
 * http 方法
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-31 23:16
 */
public enum HttpMethod {
    /**
     * GET 方法
     */
    GET,
    /**
     * POST 方法
     */
    POST,
    /**
     * PUT 方法
     */
    PUT,
    /**
     * PATCH 方法
     */
    PATCH,
    /**
     * DELETE FANGFA
     */
    DELETE,
    /**
     * 所有方法
     */
    ALL;

    /**
     * 方法匹配
     *
     * @param name 匹配的方法
     * @return 结果
     */
    public boolean match(String name) {
        return name().equals(name);
    }
}
