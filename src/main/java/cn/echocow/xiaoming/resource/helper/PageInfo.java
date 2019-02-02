package cn.echocow.xiaoming.resource.helper;

import lombok.*;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-01 21:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {

    /**
     * 分页大小
     */
    private Integer size;

    /**
     * 当前页
     */
    private Integer number;

    /**
     * 元素个数
     */
    private Long totalElements;

    /**
     * 总页码
     */
    private Integer totalPages;

    /**
     * 是否拥有上一页
     */
    private boolean previous;

    /**
     * 是否拥有下一页
     */
    private boolean next;

    /**
     * 是否拥有上一页
     *
     * @return 是否拥有上一页
     */
    public boolean hasPrevioud() {
        return this.previous;
    }

    /**
     * 是否拥有下一页
     *
     * @return 是否拥有下一页
     */
    public boolean hasNext() {
        return this.next;
    }
}
