package com.ailen.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 定位坐标实体类
 *
 * @author anshare
 * 
 * @version 1.0.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinate {
    /** 经度 */
    private double lon;
    /** 纬度 */
    private double lat;

    /**
     * 作为百度API参数的文本格式
     * @return
     */
    public String toBdParamString() {
        return lat + "," + lon;
    }
}
