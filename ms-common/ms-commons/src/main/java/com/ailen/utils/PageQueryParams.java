package com.ailen.utils;

import com.anshare.base.common.model.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class PageQueryParams extends Page {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "升序字段，多个用逗号分隔")
    private String ascs;

    @ApiModelProperty(value = "降序字段，多个用逗号分隔")
    private String descs;

    @ApiModelProperty(value = "是否查询总数", hidden=true)
    private String ifCount = "true";

}
