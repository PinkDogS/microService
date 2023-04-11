package com.ailen.core.filter;

import com.anshare.base.common.model.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * filter 基类
 *
 * @author jimin
 * @since 2023-02-07 20:59:36
 */
@Data
public class BaseFilter extends Page {
    /* id */
    @ApiModelProperty(value = "id")
    Long id;
    
    /* id 字符串，逗号分隔 */
    @ApiModelProperty(value = "id 字符串，逗号分隔")
    String ids;
    
    /* id 列表 */
    @ApiModelProperty(value = "id 列表")
    List<Long> idList;
}
