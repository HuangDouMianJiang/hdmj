package com.github.hdmj.util;

import java.util.List;

/**
 * Created by Xuefeng_wen on 2017/12/18.
 */
public class GetTrackResp {

    /** 状态码 */
    private String status;

    /** 响应信息 --- 对status的中文描述 */
    private String message;

    /** 忽略掉page_index，page_size后的轨迹点数量 --- 代表一共有多少条符合条件的track */
    private String total;

    /** 返回的结果条数 --- 	代表本页返回了多少条符合条件的轨迹点数量 */
    private String size;

    /** 此段轨迹的里程数，单位：米 */
    private String distance;

    /** 段轨迹的收费里程数，单位：米 */
    private String toll_distance;

    /** 起点信息 */
    private GetTrackPoint start_point;

    /** 终点信息 */
    private GetTrackPoint end_point;

    /** 历史轨迹点列表 */
    private List<GetTrackPoints> points;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getToll_distance() {
        return toll_distance;
    }

    public void setToll_distance(String toll_distance) {
        this.toll_distance = toll_distance;
    }

    public GetTrackPoint getStart_point() {
        return start_point;
    }

    public void setStart_point(GetTrackPoint start_point) {
        this.start_point = start_point;
    }

    public GetTrackPoint getEnd_point() {
        return end_point;
    }

    public void setEnd_point(GetTrackPoint end_point) {
        this.end_point = end_point;
    }

    public List<GetTrackPoints> getPoints() {
        return points;
    }

    public void setPoints(List<GetTrackPoints> points) {
        this.points = points;
    }
}
