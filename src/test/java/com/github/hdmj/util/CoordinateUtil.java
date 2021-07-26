package com.github.hdmj.util;

import com.google.common.base.Strings;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.distance.CartesianDistCalc;
import org.locationtech.spatial4j.distance.DistanceUtils;
import org.locationtech.spatial4j.shape.Point;
import org.locationtech.spatial4j.shape.Rectangle;
import org.locationtech.spatial4j.shape.ShapeFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * 地理位置，坐标等工具类，在spatial4j 的基础上
 *
 * @author WangShuai
 */
public class CoordinateUtil {

	private static final SpatialContext GEO = SpatialContext.GEO;

	private static final CartesianDistCalc CARTESIAN_DIST_CALC = CartesianDistCalc.INSTANCE;

	/**
	 * 给定坐标为圆心，范围是半径，画一个圆，此方法计算出了这个圆外正方形的四个点。
	 *
	 * @param lng    中心点经度
	 * @param lat    中心点纬度
	 * @param radius 范围
	 * @return
	 */
	public static Rectangle getRectangle(Double lng, Double lat, Integer radius) {
		return GEO.getDistCalc().calcBoxByDistFromPt(
			GEO.getShapeFactory().pointXY(lng, lat), radius * DistanceUtils.KM_TO_DEG, GEO, null);
	}

	/**
	 * 生成一个 点对像
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static Point point(double x, double y) {
		return GEO.getShapeFactory().pointXY(x, y);
	}

	/**
	 * 计算旋转角度
	 *
	 * @param startX
	 * @param startY
	 * @param targetX
	 * @param targetY
	 * @return
	 */
	public static double rotationAngle(double startX, double startY, double targetX, double targetY) {
		double rotation = (180 / Math.PI) * Math.atan2(targetX - startX, targetY - startY);
		return 0 < rotation ? rotation : rotation + 360;
	}

	/**
	 * 给定坐标为圆心，范围是半径，画一个圆，此方法计算出了这个圆外正方形的四个点。
	 */
	public static Rectangle getRectangle(double minX, double maxX, double minY, double maxY) {
		return GEO.getShapeFactory().rect(minX, maxX, minY, maxY);
	}

	/**
	 * 根据传入点，获取到指定旋转角度上 距离是 x 的坐标点
	 *
	 * @param lng      经度
	 * @param lat      纬度
	 * @param distance 距离
	 * @param bearing  旋转角度
	 * @return 坐标点
	 */
	public static Point getPoint(Double lng, Double lat, Integer distance, double bearing) {
		return CARTESIAN_DIST_CALC.pointOnBearing(
			GEO.getShapeFactory().pointXY(lng, lat),
			distance * DistanceUtils.KM_TO_DEG,
			bearing,
			GEO,
			null);
	}

	/**
	 * 判断图形点是否再该区域范围内
	 *
	 * @param rectangle 区域图形
	 * @param lng       经度
	 * @param lat       纬度
	 * @return 是否
	 */
	public static boolean isInRange(Rectangle rectangle, double lng, double lat) {
		return rectangle.getMinX() <= lng
			&& lng <= rectangle.getMaxX()
			&& rectangle.getMinY() <= lat
			&& lat <= rectangle.getMaxX();
	}

	/**
	 * 判断目标地址是否在指定区域
	 *
	 * @param minLongitude    最小经度
	 * @param maxLongitude    最小经度
	 * @param minLatitude     最小纬度
	 * @param maxLatitude     最大纬度
	 * @param targetLongitude 目标经度
	 * @param targetLatitude  目标纬度
	 * @return 是否在区域内
	 */
	public static boolean isSpecificArea(double minLongitude,
										 double maxLongitude,
										 double minLatitude,
										 double maxLatitude,
										 double targetLongitude,
										 double targetLatitude) {
		// 小于
		return minLongitude <= targetLongitude && targetLongitude <= maxLongitude
			&& minLatitude <= targetLatitude && targetLatitude <= maxLatitude;
	}

	/**
	 * 判断图形点是否再该区域范围内
	 *
	 * @param latitudeStatistics  纬度最大最小统计
	 * @param longitudeStatistics 经度最大最小统计
	 * @param lng                 经度
	 * @param lat                 纬度
	 * @return 是否
	 */
	public static boolean isInRange(DoubleSummaryStatistics latitudeStatistics, DoubleSummaryStatistics longitudeStatistics, double lng, double lat) {
		return longitudeStatistics.getMin() <= lng
			&& lng <= longitudeStatistics.getMax()
			&& latitudeStatistics.getMin() <= lat
			&& lat <= latitudeStatistics.getMax();
	}

	/**
	 * 对坐标点进行排序
	 *
	 * @param doubles 坐标点
	 * @return
	 */
	public static DoubleSummaryStatistics sortedPoints(Double... doubles) {
		List<Double> latitudeList = new ArrayList<>();
		Collections.addAll(latitudeList, doubles);
		return latitudeList.stream().mapToDouble(Double::doubleValue).summaryStatistics();
	}

	/**
	 * 简化调用 getRectangle
	 *
	 * @param lng
	 * @param lat
	 * @param radius
	 * @return
	 */
	public static Rectangle getRectangle(String lng, String lat, Integer radius) {
		Double lngDbl = null;
		Double latDbl = null;
		try {
			lngDbl = Double.parseDouble(lng);
			latDbl = Double.parseDouble(lat);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
		return getRectangle(lngDbl, latDbl, radius);
	}

	public static Double getDistance(String lngAStr, String latAStr, String lngBStr, String latBStr) {
		if (Strings.isNullOrEmpty(lngAStr) || Strings.isNullOrEmpty(latAStr) || Strings.isNullOrEmpty(lngBStr) || Strings.isNullOrEmpty(latBStr)) {
			return -1D;
		}
		Double lngA = null;
		Double latA = null;
		Double lngB = null;
		Double latB = null;
		try {
			lngA = Double.parseDouble(lngAStr);
			latA = Double.parseDouble(latAStr);
			lngB = Double.parseDouble(lngBStr);
			latB = Double.parseDouble(latBStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return -1D;
		}
		return getDistance(lngA, latA, lngB, latB);
	}


	public static Double getDistance(Double lngA, Double latA, Double lngB, Double latB) {
		ShapeFactory shapeFactory = GEO.getShapeFactory();
		double distance = GEO.getDistCalc().distance(shapeFactory.pointXY(lngA, latA), shapeFactory.pointXY(lngB, latB))
			* DistanceUtils.DEG_TO_KM;
		distance = new BigDecimal(distance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return distance;
	}

	/**
	 * 计算距离
	 *
	 * @param startX
	 * @param startY
	 * @param targetX
	 * @param targetY
	 * @return
	 */
	public static long getDistanceMiles(double startX, double startY, double targetX, double targetY) {
		ShapeFactory shapeFactory = GEO.getShapeFactory();
		double distance = GEO.getDistCalc().distance(shapeFactory.pointXY(startX, startY), shapeFactory.pointXY(targetX, targetY))
			* DistanceUtils.DEG_TO_KM;
		return BigDecimal.valueOf(distance).multiply(BigDecimal.valueOf(1000)).intValue();
	}

	public static Double getDistance(Point a, Point b) {
		double distance = SpatialContext.GEO.calcDistance(a, b) * DistanceUtils.DEG_TO_KM;
		distance = new BigDecimal(distance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return distance;
	}
}
