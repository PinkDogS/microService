package com.ailen.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RouteInfo {
    Coordinate orig;
    Coordinate dest;
    /**
     * 线路距离的文本描述,文本描述的单位有米、公里两种
     */
    String distanceText;
    /**
     * 线路距离的数值,单位为米,若没有计算结果，值为0
     */
    double distance;

    /**
     * 路线耗时的文本描述, 文本描述的单位有分钟、小时两种
     */
    String durationText;

    /**
     * 路线耗时的数值, 数值的单位为秒。若没有计算结果，值为0
     */
    double duration;
}
