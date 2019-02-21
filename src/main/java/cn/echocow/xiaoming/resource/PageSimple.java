package cn.echocow.xiaoming.resource;

import lombok.*;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-01 21:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageSimple {

    /**
     * 分页大小
     */
    private Long size;

    /**
     * 当前页
     */
    private Long current;

    /**
     * 元素个数
     */
    private Long total;

    /**
     * 总页码
     */
    private Long pages;

    /**
     * 是否拥有上一页
     *
     * @return 是否拥有上一页
     */
    public boolean hasPrevioud() {
        return (current - 1) < 1;
    }

    /**
     * 是否拥有下一页
     *
     * @return 是否拥有下一页
     */
    public boolean hasNext() {
        return current + 1 > pages;
    }
}
