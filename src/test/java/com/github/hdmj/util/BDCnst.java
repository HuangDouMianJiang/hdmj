package com.github.hdmj.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Xuefeng_Wen on 2017/11/22.
 *
 * @implNote 百度相关常量类
 */
public class BDCnst {

    /**
     * 用户的ak，授权使用 浏览器端
     * ePt3xdbTn0hvsFuMDool7abCi3vNZtnk IHao
     */
//    public static final String BD_AK = "ePt3xdbTn0hvsFuMDool7abCi3vNZtnk";
    public static final String BD_AK = "TQySEBWXIP0Q5Rc7FoqyWUGvsBPpiIG4";

    /**
     * 百度地图服务端使用的ak
     */
//    public static final String BD_SERVICE_AK = "QhQunVG6AIsScCHQZgG4WkMyTt9bZqn8";
    public static final String BD_SERVICE_AK = "oPFiQb37SrteGXibyvvR0lfaxF0NcKGL";

    /**
     * 服务ID(Serivce ID)
     * 148170 IHao
     */
//    public static final String BD_SERVICE_ID = "148170";
    public static final String BD_SERVICE_ID = "226779";

    /**
     * ihao工程师 208899
     */
//    public static final String BD_ENGINEER_SERVICE_ID = "208899";
    public static final String BD_ENGINEER_SERVICE_ID = "226779";

    /**
     * 电子围栏服务ID
     */
//    public static final Integer BD_FENCE_SERVICE_ID = 205366;
    public static final Integer BD_FENCE_SERVICE_ID = 226779;

    /**
     * 请求的时候 entityName 作为key
     */
    public static final String ENTITY_NAME_KEY = "entity_name";

    /**
     * 进去围栏
     */
    public static final String ENTER_FENCE = "enter";

    /**
     * 离开围栏
     */
    public static final String EXIT_FENCE = "exit";

    /**
     * 创建圆形围栏
     */
    public static final String CREATE_CIRCLE_FENCE = "http://yingyan.baidu.com/api/v3/fence/createcirclefence";

    /**
     * 更新圆形围栏
     */
    public static final String UPDATE_CIRCLE_FENCE = "http://yingyan.baidu.com/api/v3/fence/updatecirclefence";

    /**
     * 删除圆形围栏
     */
    public static final String DELETE_CIRCLE_FENCE = "http://yingyan.baidu.com/api/v3/fence/delete";

    /**
     * 增加围栏需监控的entity
     */
    public static final String ADD_MONITORED_PERSON  = "http://yingyan.baidu.com/api/v3/fence/addmonitoredperson";

    /**
     * 删除围栏可去除监控的entity
     */
    public static final String DELETE_MONITORED_PERSON  = "http://yingyan.baidu.com/api/v3/fence/deletemonitoredperson";

    /**
     * 创建一个新的轨迹数据导出任务，下载该service 内一段时间内的全部轨迹。
     * 注意：
     * 1. 只能下载距当前时间6小时之前的轨迹，例如：2017-8-7 10:00创建的下载任务只能下载2017-8-7 4:00之前产生的轨迹
     * 2. 每一个任务最多下载24小时时长的轨迹。例如，若下载7天的轨迹，则需创建7个任务
     * 3. 每个service_id同时只允许存在10个未完成任务，超过10个则返回创建失败，请等待现有的任务处理完之后再创建新的任务
     */
    public static final String EXPORT_TRACK_CREATE_JOB = "http://yingyan.baidu.com/api/v3/export/createjob";

    /**
     * 查询任务池中的任务，任务池中包括以下几类任务：
     * 1. 已创建尚未开始执行的任务
     * 2. 正在执行的任务
     * 3. 已完成的任务，但完成时间不超过48小时（注：已完成的任务会在48小时之后自动清理）
     * 已完成的任务会返回file_url，将地址粘贴至浏览器或使用其他下载方法，即可获得轨迹数据文件。
     */
    public static final String EXPORT_TRACK_GET_JOB = "http://yingyan.baidu.com/api/v3/export/getjob";

    /**
     * 删除任务
     */
    public static final String EXPORT_TRACK_DELETE_JOB = "http://yingyan.baidu.com/api/v3/export/deletejob";

    /**
     * addpoints——批量添加轨迹点
     * 一次上传多个轨迹点.可上传一个 entity 的多个轨迹点，或多个entity的多个轨迹点，并且可以携带自定义字段的信息。
     */
    public static final String UPLOAD_TRACK_POINTS = "http://yingyan.baidu.com/api/v3/track/addpoints";

    public static final String DISTRICT_SEARCH  = "http://yingyan.baidu.com/api/v3/entity/districtsearch";

    public static Map<String, Object> getTrackParamMap() {
        Map<String, Object> map = new HashMap<String, Object>(8);
        map.put("ak", BDCnst.BD_AK);
        map.put("service_id", BDCnst.BD_SERVICE_ID);
        return map;
    }

    /**
     * 电子围栏请求参数map
     * @return
     */
    public static ConcurrentHashMap<String, Object> getFenceParamMap() {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>(12);
        map.put("ak", BDCnst.BD_AK);
        map.put("coord_type", "bd09ll");
        return map;
    }

}
