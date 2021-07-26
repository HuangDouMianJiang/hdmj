package com.github.hdmj.util;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xuefeng_wen
 * @date 2017/12/5
 * 同步百度 车机，APP数据
 */
public class OrderUtil {
    /**
     * gettrack——轨迹查询与纠偏 GET请求
     */
    private static final String TRACK_GETTRACK_URL = "http://yingyan.baidu.com/api/v3/track/gettrack";

    /**
     * getdistance——查询轨迹里程 GET请求
     */
    private static final String TRACK_GETDISTANCE_URL = "http://yingyan.baidu.com/api/v3/track/getdistance";

    /**
     * getlatestpoint——实时纠偏 GET请求
     */
    private static final String GETLATESTPOINT_URL = "http://yingyan.baidu.com/api/v3/track/getlatestpoint";


    /**
     * 查询驾车路线
     */
    private static final String DRIVING_ROUTE = "http://api.map.baidu.com/direction/v2/driving";


    /**
     * 获取订单轨迹 redis key 其中 map 是 ConcurrentHashMap key 是时间 value 是订单 ID
     */
    public static final String FETCH_ORDER_TRACK_MAP_KEY = "FETCH_ORDER_TRACK_MAP_KEY";


    /**
     * 计算2个时间相差的小时数
     *
     * @param nowDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public static Long getDatePoor(Date nowDate, Date endDate) {
        long hour = 0L;
        try {
//            long nd = 1000 * 24 * 60 * 60;
//            long nh = 1000 * 60 * 60;
            // 获得两个时间的毫秒时间差异
            long diff = endDate.getTime() - nowDate.getTime();
            // 计算差多少小时
            hour = diff / 1000 / 3600;
            if (diff / 1000 % 3600 > 0) {
                hour++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hour;
    }

    /**
     * 计算2个时间相差的秒数
     *
     * @param nowDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public static Long getDateSecond(Date nowDate, Date endDate) {
        long hour = 0L;
        try {
            hour = ((endDate.getTime() - nowDate.getTime()) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hour;
    }

    /**
     * 分页查询轨迹点
     *
     * @param entityName entity唯一标识
     * @param startTime  轨迹开始时间
     * @param endTime    轨迹结束时间
     * @param pageNumber 分页
     * @return
     */
    public static GetTrackResp getTrack(String entityName, String startTime, String endTime, int pageNumber) {
        GetTrackResp getTrackResp = null;
        try {
            Map<String, Object> map = BDCnst.getTrackParamMap();
            map.put("entity_name", entityName.trim()); //唯一标识
            map.put("start_time", startTime); //起始时间
            map.put("end_time", endTime); //结束时间
            map.put("is_processed", "1"); //是否返回纠偏后轨迹 1是
            //need_mapmatch 绑路
            //need_denoise  去燥
            //need_vacuate  抽稀
            //transport_mode 交通方式
            //radius_threshold 定位精度过滤
            map.put("process_option", "need_mapmatch=" + "1" +
                    ",need_denoise=" + "1" +
                    ",need_vacuate=" + "0" +
                    ",transport_mode=" + "driving" +
                    ",radius_threshold=" + "200"); //纠偏选项 3绑路
            //在里程计算时，两个轨迹点定位时间间隔5分钟以上，被认为是中断。中断轨迹提供以下5种里程估算方式。
            //no_supplement：不补充，中断两点间距离不记入里程。
            //straight：使用直线距离补充
            //driving：使用最短驾车路线距离补充
            //riding：使用最短骑行路线距离补充
            //walking：使用最短步行路线距离补充
            map.put("supplement_mode", "no_supplement"); //里程补偿方式 直线补充
            map.put("page_index", pageNumber); //分页索引
            map.put("page_size", 5000); //分页大小 12 * 60 （1小时）
            String str = HttpRequest.get(TRACK_GETTRACK_URL + "?" + getParamsFromMap(map)).execute().body();
            getTrackResp = JSONObject.parseObject(str, GetTrackResp.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return getTrackResp;
    }

    /**
     * 根据map获取get的参数key=value&key=value
     *
     * @param paramsMap
     * @return
     */
    public static String getParamsFromMap(Map<String, Object> paramsMap) {
        String params = "";
        int paramsCount = paramsMap == null ? 0 : paramsMap.keySet().size();
        if (paramsMap != null && paramsCount != 0) {
            for (String key : paramsMap.keySet()) {
                params += key + "=" + paramsMap.get(key);
                if (paramsCount > 1) {
                    params += "&";
                }
                paramsCount--;
            }
        }
        return params;
    }

    /**
     * 计算两个坐标点间的距离
     *
     * @param fromLongitude 起点-经度
     * @param fromLatitude  起点-纬度
     * @param toLongitude   终点-经度
     * @param toLatitude    终点-纬度
     * @return
     */
    public static long distanceOfTwoPoints(float fromLongitude, float fromLatitude, float toLongitude, float toLatitude) {
        return Math.round(6378.138 * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin((fromLatitude * Math.PI / 180 - toLatitude * Math.PI / 180) / 2), 2) + Math.cos(fromLatitude * Math.PI / 180) * Math.cos(toLatitude * Math.PI / 180) * Math.pow(Math.sin((fromLongitude * Math.PI / 180 - toLongitude * Math.PI / 180) / 2), 2))) * 1000);
    }

    /**
     * 计算两个坐标点间的距离
     *
     * @param fromLongitude 起点-经度
     * @param fromLatitude  起点-纬度
     * @param toLongitude   终点-经度
     * @param toLatitude    终点-纬度
     * @return
     */
    public static long distanceOfTwoPoints(String fromLongitude, String fromLatitude, String toLongitude, String toLatitude) {
        return distanceOfTwoPoints(parseFloat(fromLongitude), parseFloat(fromLatitude), parseFloat(toLongitude), parseFloat(toLatitude));
    }

    public static Float parseFloat(String str) {
        Float f = null;
        try {
            f = Float.parseFloat(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public static long parseTime(String create_time) {
        return Long.parseLong(create_time.replace("-", "").replace(" ", "").replace(":", "") + "000");
    }
}
