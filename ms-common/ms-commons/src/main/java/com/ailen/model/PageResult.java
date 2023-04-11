package com.ailen.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long pageNum;
    private Long pageSize;
    private Long total;
    private List<T> data;

    public PageResult(Long total, List<T> data) {
        this.total = total;
        this.data = data;
    }

    public PageResult(Long total, List<T> data, Long pageNum, Long pageSize) {
        this.total = total;
        this.data = data;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

}
