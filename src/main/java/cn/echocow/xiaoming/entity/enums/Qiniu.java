package cn.echocow.xiaoming.entity.enums;

/**
 * 七牛云
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-08 23:54
 */
public enum Qiniu {
    /**
     * 华东区域
     */
    EAST("华东"),
    /**
     * 华南区域
     */
    SOUTH("华南"),
    /**
     * 华北
     */
    NORTH("华北"),
    /**
     * 北美
     */
    AMERICA("北美"),
    /**
     * 东南亚
     */
    Asia("东南亚");
    /**
     * 地区
     */
    private final String area;

    /**
     * 构造
     *
     * @param area 地区
     */
    Qiniu(String area) {
        this.area = area;
    }

    /**
     * 是否匹配
     *
     * @param area 地区
     * @return 结果
     */
    public boolean match(String area) {
        return this.area.equals(area);
    }
}




