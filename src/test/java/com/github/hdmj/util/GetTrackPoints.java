package com.github.hdmj.util;

/**
 * Created by Xuefeng_Wen on 2017/12/18.
 * @apiNote 历史轨迹点信息
 */
public class GetTrackPoints {

    /** 经度 */
    private String longitude;

    /** 纬度 */
    private String latitude;

    /** 定位时的设备时间 */
    private String loc_time;

    /** 创建时间 */
    private String create_time;

    /** 方向 */
    private String direction;

    /** 高度 */
    private String height;

    /** 速度 */
    private String speed;

    /** 定位精度 */
    private String radius;

    /** 楼层 */
    private String floor;

    /** 坐标类型 */
    private String coord_type;

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

    public String getLoc_time() {
        return loc_time;
    }

    public void setLoc_time(String loc_time) {
        this.loc_time = loc_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getHeight() {
        return null == height || 0 == height.length() ? "0" : height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSpeed() {
        return null == speed || 0 == speed.length() ? "0" : speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getFloor() {
        return null == floor || 0 == floor.length() ? "0" : floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getCoord_type() {
        return coord_type;
    }

    public void setCoord_type(String coord_type) {
        this.coord_type = coord_type;
    }
}
