package com.ailen.feign.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * SysDictItem DTO对象
 *
 * @author jimin
 * @since 2023-02-14 20:27:31
 */
@Data
@Accessors(chain = true)
public class DictItemDto {
    
    @ApiModelProperty("字典代码")
    private String dictCode;
    
    @ApiModelProperty("字典项值")
    private String itemValue;

    @ApiModelProperty("字典项文本")
    private String itemText;
    
}
