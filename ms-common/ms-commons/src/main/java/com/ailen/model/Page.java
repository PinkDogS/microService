package com.ailen.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 分页查询条件
 *
 * @author anshare
 * 
 */
@Setter
@Getter
public class Page implements Serializable {

    /**
     * 页码，从1开始
     */
    @ApiModelProperty(value = "页码，从1开始")
    private int pageNum = 1;

    /**
     * 页面大小
     */
    @ApiModelProperty(value = "页面大小，默认50")
    private int pageSize = 50;
}
