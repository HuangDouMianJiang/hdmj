package com.github.hdmj.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.distance.CartesianDistCalc;
import org.locationtech.spatial4j.distance.DistanceUtils;
import org.locationtech.spatial4j.shape.Point;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DrivingTest {

    /**
     * 计算车辆续航里程
     *
     * @param currentSoc       当前SOC
     * @param lowSoc           　　进站充电最低SOC
     * @param powerConsumption 　百公里电耗
     * @param fullElectricity  　满电电量
     * @return BigDecimal 剩余续航里程
     */
    public BigDecimal calculateBatteryMileage(int currentSoc, int lowSoc, int powerConsumption, int fullElectricity) {
        if (currentSoc <= lowSoc) {
            return BigDecimal.ZERO;
        }
        // 1、 fullElectricity * ((currentSoc - lowSoc) / 100) = 可续航行kWh
        BigDecimal availableKwh = new BigDecimal(fullElectricity * (currentSoc - lowSoc)).divide(new BigDecimal(100), 3, BigDecimal.ROUND_HALF_UP);
        // 2、 可续航行kWh / powerConsumption = 当前电量可续航X百公里
        BigDecimal currentBatteryHundredKm = availableKwh.divide(new BigDecimal(powerConsumption), 3, BigDecimal.ROUND_HALF_UP);
        // 3、 当前电量可续航X百公里 * 100 = X 公里
        return currentBatteryHundredKm.multiply(new BigDecimal(100));
    }

    /**
     * 计算充电时间
     *
     * @param voltage         电压
     * @param electricCurrent 电流
     * @param fullElectricity 满电电量 x kWh
     * @return 充电时间
     */
    public int chargingTime(BigDecimal voltage, BigDecimal electricCurrent, int fullElectricity) {
        BigDecimal power = voltage.multiply(electricCurrent);
        return new BigDecimal(fullElectricity).multiply(new BigDecimal(1000)).divide(power, 4, RoundingMode.HALF_DOWN).multiply(new BigDecimal(60)).intValue();
    }

    @Test
    public void calculateBatteryMileageTest() {
        BigDecimal bigDecimal = this.calculateBatteryMileage(82, 15, 20, 70);
        System.out.println(bigDecimal);
    }

    @Test
    public void chargingTimeTest() {
        System.out.println(chargingTime(new BigDecimal(200), new BigDecimal("32.01"), 70));
    }

    @Test
    public void test1() {
        Integer a = new Integer(2);
        Integer b = new Integer(2);
        System.out.println(a.equals(b));
    }

    @Test
    public void testLeftOrRight() {
        String point = "{\"currentDistance\":209385,\"isTollRoad\":true,\"latitude\":38.641558,\"longitude\":115.200109,\"orientation\":\"西南\",\"thinOut\":true}";
        String stationInfo = "{\"address\":\"京港澳高速望都服务区（北京方向）\",\"defaultPrice\":\"￥1.4\",\"des\":\"地上、已建成、已测试\",\"distance\":\"99km\",\"distanceBigDecimal\":4037,\"id\":\"MA002TMQX130631MA002TMQX0002\",\"nextDistance\":0,\"openTime\":\"全天开放\",\"operatorId\":\"MA002TMQX\",\"operatorName\":\"运营商名字\",\"opinionCount\":15,\"parkFee\":\"0（参照停车场实际费用）\",\"previousDistance\":0,\"score\":4.5,\"stationLatGcj02\":38.662605000000000,\"stationLngGcj02\":115.226980000000000,\"stationName\":\"京港澳高速望都服务区充电站（北京方向）\",\"toChargingStation\":false,\"userPrice\":\"￥1.4\"}";
        JSONObject pointJson = JSON.parseObject(point);
        JSONObject stationJson = JSON.parseObject(stationInfo);
        double pointLatitude = pointJson.getDoubleValue("latitude");
        double pointLongitude = pointJson.getDoubleValue("longitude");
        double stationLatitude = stationJson.getDoubleValue("stationLatGcj02");
        double stationLongitude = stationJson.getDoubleValue("stationLngGcj02");
        String orientation = pointJson.getString("orientation");
        boolean flag = false;
        // 判断当前前往方向并计算是否再当前的方向的右侧
        switch (orientation) {
            case "东":
                flag = pointLatitude > stationLatitude;
            case "南":
                flag = pointLongitude > stationLongitude;
            case "西":
                flag = pointLatitude < stationLatitude;
            case "北":
                flag = pointLongitude < stationLongitude;
            case "东南":
                flag = pointLatitude > stationLatitude;
            case "东北":
                flag = pointLatitude > stationLatitude;
            case "西南":
                flag = pointLatitude < stationLatitude;
            case "西北":
                flag = pointLatitude < stationLatitude;
            default:
                flag = false;
        }
        System.out.println(flag);
    }

    @Test
    public void testDistance() {
        double pointLatitude = 38.664865D;
        double pointLongitude = 115.226102D;
        double stationLongitude = 115.22378500D;
        double stationLatitude = 38.6663170D;
        System.out.println(CoordinateUtil.getDistanceMiles(
                pointLongitude,
                pointLatitude,
                stationLongitude,
                stationLatitude
        ));
    }

    @Test
    public void testBearingDistance() {
        double pointLatitude = 38.664865D;
        double pointLongitude = 115.226102D;
        CartesianDistCalc cartesianDistCalc = CartesianDistCalc.INSTANCE;
        SpatialContext spatialContext = SpatialContext.GEO;
        Point point = SpatialContext.GEO.getShapeFactory().pointXY(pointLongitude, pointLatitude);
        Point point1 = cartesianDistCalc.pointOnBearing(
                point,
                5 * DistanceUtils.KM_TO_DEG,
                90D,
                spatialContext,
                null);
        System.out.println(point1.getX() + "," + point1.getY());
    }

    @Test
    public void testListCopy() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
        List<Integer> list2 = new ArrayList<>(list);
        for (Integer integer : list2) {
            if (integer == 2) {
                integer = 100;
            }
        }
        list.forEach(System.out::println);
        list2.forEach(System.out::println);
    }


    @Test
    public void getAllTrack() {
        Long end = 1621353599L;
        Long step = 21600L;
        Long curStart = 1621313100L;
        Long curEnd = curStart + step;
        String entityName = "868053052321874";
        System.out.println("start");
        //以6小时为一个段，分段请求轨迹数据，防止百度鹰眼返回错误（百度鹰眼限制只能查询24小时之内的数据，并且如果数据量过大3秒没返回的话，将会报错）
        while (true) {
            //第一层循环来处理时间分段
            if (curEnd > end) {
                curEnd = end;
            }
            int pageNumber = 1;
            int count = 0;//实际查询的数据总数量
            //如果分段的结束时间比结束时间小，说明还可以继续往下进行
            while (true) {
                //第二层循环来处理数据分页
                // 3，查询鹰眼数据
                GetTrackResp getTrackResp = OrderUtil.getTrack(entityName, curStart.toString(), curEnd.toString(), pageNumber);
                if (null != getTrackResp && "0".equals(getTrackResp.getStatus()) && null != getTrackResp.getPoints()) {
                    getTrackResp.getPoints().forEach(o -> {
                        System.out.println("INSERT INTO wangyou.ihao_order_track ( ORDER_ID, TYPE, LONGITUDE, LATITUDE, LOC_TIME, DIRECTION, HEIGHT, SPEED, FLOOR) VALUES (61, 2, '" + o.getLongitude() + "', '" + o.getLatitude() + "', " + OrderUtil.parseTime(o.getCreate_time()) + ", " + o.getDirection() + ", " + o.getHeight() + ", " + o.getSpeed() + ", '0');");
                    });
                    //本次返回的数量相加
                    count += Integer.parseInt(getTrackResp.getSize());
                    if (count >= Integer.parseInt(getTrackResp.getTotal())) {
                        //如果累计返回的数量等于总数量
                        break;
                    }
                    pageNumber++;
                } else {
                    break;
                }
            }
            if (curEnd.longValue() == end) {
                break;
            }
            //每次加6小时
            curStart = curEnd + 1;
            curEnd = curStart + step;
        }
        System.out.println("end");
    }

    @Test
    public void testLong() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String time = "2021-05-21 12:12:00.001";
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
//        localDateTime = localDateTime.withSecond(1).withNano(1);
        System.out.println(localDateTime.toInstant(ZoneOffset.UTC));

    }

}
