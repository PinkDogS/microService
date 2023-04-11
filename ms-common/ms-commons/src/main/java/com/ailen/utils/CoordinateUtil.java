package com.ailen.utils;

import com.anshare.base.common.vo.Coordinate;

import static java.lang.StrictMath.*;

/**
 * 地图坐标系转换工具类
 *
 * @author anshare
 *
 * @version 1.0.0
 **/
public class CoordinateUtil {
    private static final double pi = 3.14159265358979324;
    private static final double a = 6378245.0;
    private static final double ee = 0.00669342162296594323;
    private static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    public static double EARTH_RADIUS = 6378.137;

    private static boolean outOfChina(double lat, double lon) {
        return lon < 72.004 || lon > 137.8347 || lat < 0.8293 || lat > 55.8271;

    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * sqrt(abs(x));
        ret += (20.0 * sin(6.0 * x * pi) + 20.0 * sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * sin(y * pi) + 40.0 * sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * sin(y / 12.0 * pi) + 320 * sin(y * pi / 30.0)) * 2.0 / 3.0;

        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * sqrt(abs(x));
        ret += (20.0 * sin(6.0 * x * pi) + 20.0 * sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * sin(x * pi) + 40.0 * sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * sin(x / 12.0 * pi) + 300.0 * sin(x / 30.0 * pi)) * 2.0 / 3.0;

        return ret;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两点坐标计算两点距离(百度算法)
     *
     * @param lat1 纬度
     * @param lng1 经度
     * @param lat2
     * @param lng2
     * @return
     */
    public static Double getDistance(Double lat1, Double lng1, Double lat2, Double lng2) {
        if (lat1 == null || lng1 == null || lat2 == null || lng2 == null) {
            return 0d;
        }
        double radLat1 = rad(lat1);

        double radLat2 = rad(lat2);

        double a = radLat1 - radLat2;

        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)

                + Math.cos(radLat1) * Math.cos(radLat2)

                * Math.pow(Math.sin(b / 2), 2)));

        s = s * EARTH_RADIUS;
        // 精确到 0.1米
        s = Math.round(s * 10000) / 10000.0 * 1000;

        return s;
    }

    /**
     * GPS坐标转火星坐标
     *
     * @param gps
     * @return
     */
    public static Coordinate gps84ToGcj02(Coordinate gps) {
        if (outOfChina(gps.getLat(), gps.getLon())) {
            return new Coordinate(gps.getLon(), gps.getLat());
        }
        double dLat = transformLat(gps.getLon() - 105.0, gps.getLat() - 35.0);
        double dLon = transformLon(gps.getLon() - 105.0, gps.getLat() - 35.0);
        double radLat = gps.getLat() / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = gps.getLat() + dLat;
        double mgLon = gps.getLon() + dLon;
        return new Coordinate(mgLon, mgLat);
    }

    /**
     * 火星坐标转百度坐标
     *
     * @param gcj
     * @return
     */
    public static Coordinate gcj02ToBd09(Coordinate gcj) {
        double x = gcj.getLon(), y = gcj.getLat();
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new Coordinate(bd_lon, bd_lat);
    }

    /**
     * 火星坐标转gps坐标
     *
     * @param gcj
     * @return
     */
    public static Coordinate gcjToGps84(Coordinate gcj) {
        Coordinate gps = transform(gcj);
        double lontitude = gcj.getLon() * 2 - gps.getLon();
        double latitude = gcj.getLat() * 2 - gps.getLat();
        return new Coordinate(lontitude, latitude);
    }

    /**
     * 百度坐标转火星坐标
     *
     * @param bd
     * @return
     */
    public static Coordinate bd09ToGcj02(Coordinate bd) {
        double x = bd.getLon() - 0.0065;
        double y = bd.getLat() - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new Coordinate(gg_lon, gg_lat);
    }

    /**
     * 百度坐标转gps坐标
     *
     * @param bd
     * @return
     */
    public static Coordinate bdToGps(Coordinate bd) {
        Coordinate gcj02 = bd09ToGcj02(bd);
        Coordinate map84 = gcjToGps84(gcj02);
        return map84;
    }

    /**
     * gps坐标转百度坐标
     *
     * @param gps
     * @return
     */
    public static Coordinate gpsToBd(Coordinate gps) {
        Coordinate gcj02 = gps84ToGcj02(gps);
        Coordinate bd09 = gcj02ToBd09(gcj02);
        return bd09;
    }

    public static Coordinate transform(Coordinate coor) {
        double dLat = transformLat(coor.getLon() - 105.0, coor.getLat() - 35.0);
        double dLon = transformLon(coor.getLon() - 105.0, coor.getLat() - 35.0);
        double radLat = coor.getLat() / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = coor.getLat() + dLat;
        double mgLon = coor.getLon() + dLon;
        return new Coordinate(mgLon, mgLat);
    }

    public static void main(String[] args) {
        Coordinate coordinate = new Coordinate();
        coordinate.setLon(118.726738);
        coordinate.setLat(31.998093);
        Coordinate gpsCoordinate = bdToGps(coordinate);
        System.out.println(gpsCoordinate.toBdParamString());
//        System.out.println(gpsCoordinate.getLat());
//        Coordinate bdCoordinate = gpsToBd(gpsCoordinate);
//        System.out.println(bdCoordinate.getLon());
//        System.out.println(bdCoordinate.getLat());
//        double i = getDistance(23d, 113d, 23d, 113.5);
//        System.out.println(i);
    }

}
