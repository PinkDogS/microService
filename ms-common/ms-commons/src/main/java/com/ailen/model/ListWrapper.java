package com.ailen.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果
 *
 * @author anshare
 *
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListWrapper<T> implements Serializable {

    /**
     * 总数
     */
    private Long total;

    /**
     * 列表
     */
    private List<T> list;
}
