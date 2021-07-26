package com.github.hdmj.util;

/**
 * Created by Xuefeng_Wen on 2017/12/18.
 * @apiNote 终点信息
 */
public class GetTrackPoint {

    /** 经度 */
    private String longitude;

    /** 纬度 */
    private String latitude;

    /** 坐标类型 */
    private String coord_type;

    /** 定位时间 */
    private String loc_time;

    // END

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCoord_type() {
        return coord_type;
    }

    public void setCoord_type(String coord_type) {
        this.coord_type = coord_type;
    }

    public String getLoc_time() {
        return loc_time;
    }

    public void setLoc_time(String loc_time) {
        this.loc_time = loc_time;
    }

}
