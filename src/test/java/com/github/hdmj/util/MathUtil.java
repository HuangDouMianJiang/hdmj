package com.github.hdmj.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author czh
 * @version 1.0
 * @date 2020/7/15 9:48
 * @Description
 */
public class MathUtil {
    private static final int DEFAULT_DIV_SCALE = 32;
    public static final String DF_WITH_0 = "0.00";
    public static final String DF_WITHOUT_0 = "#.##";
    private static ThreadLocal<Map<String, DecimalFormat>> dfMap = ThreadLocal.withInitial(() -> {
        return new HashMap();
    });

    private static DecimalFormat getDf(String pattern) {
        Map<String, DecimalFormat> decimalFormatMap = (Map) dfMap.get();
        DecimalFormat df = (DecimalFormat) decimalFormatMap.get(pattern);
        if (df == null) {
            df = new DecimalFormat(pattern);
            decimalFormatMap.put(pattern, df);
        }

        return df;
    }

    public static String fen2Yuan(Long fen) {
        return fen2Yuan(fen, (String) null, "0.00");
    }

    public static String fen2Yuan(Long fen, String defaultVal) {
        return fen2Yuan(fen, defaultVal, "0.00");
    }

    public static String fen2Yuan(Long fen, String defaultVal, String pattern) {
        if (fen == null) {
            return defaultVal;
        } else {
            double d = fen.doubleValue() / 100.0D;
            return getDf(pattern).format(d);
        }
    }

    public static String format(Double value) {
        return format(value, (String) null, "0.00");
    }

    public static String format(Double value, String defaultVal) {
        return format(value, defaultVal, "0.00");
    }

    public static String format(Double value, String defaultVal, String pattern) {
        return value == null ? defaultVal : getDf(pattern).format(value);
    }

    private MathUtil() {
    }

    public static Double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static Double add(double v1, double v2, double v3) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        return b1.add(b2).add(b3).doubleValue();
    }

    public static Double add(double v1, double v2, double v3, double v4) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        return b1.add(b2).add(b3).add(b4).doubleValue();
    }

    public static Double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static Double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static Double mul(double v1, double v2, double v3) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        return b1.multiply(b2).multiply(b3).doubleValue();
    }

    public static Double mul(double v1, double v2, double v3, double v4) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        return b1.multiply(b2).multiply(b3).multiply(b4).doubleValue();
    }

    public static Double div(double v1, double v2) {
        return div(v1, v2, 32);
    }

    public static Double div(double v1, double v2, double v3) {
        return div(v1, v2, v3, 32);
    }

    public static Double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.divide(b2, scale, 4).doubleValue();
        }
    }

    public static Double div(double v1, double v2, double v3, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            BigDecimal b3 = new BigDecimal(Double.toString(v3));
            return b1.divide(b2, scale, 4).divide(b3, 4).doubleValue();
        }
    }

    public static Double div(int v1, int v2) {
        return div(v1, v2, 32);
    }

    public static Double div(int v1, int v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b1 = new BigDecimal(Integer.toString(v1));
            BigDecimal b2 = new BigDecimal(Integer.toString(v2));
            return b1.divide(b2, scale, 4).doubleValue();
        }
    }

    public static Double sum(double[] d) {
        if (d != null && d.length != 0) {
            BigDecimal sum = new BigDecimal("0");

            for (int i = 0; i < d.length; ++i) {
                sum.add(new BigDecimal(Double.toString(d[i])));
            }

            return sum.doubleValue();
        } else {
            return 0.0D;
        }
    }

    public static Double average(double[] d) {
        if (d != null && d.length != 0) {
            BigDecimal sum = new BigDecimal("0");

            for (int i = 0; i < d.length; ++i) {
                sum.add(new BigDecimal(Double.toString(d[i])));
            }

            sum.divide(new BigDecimal(Integer.toString(d.length)), 32, 4);
            return sum.doubleValue();
        } else {
            return 0.0D;
        }
    }

    public static Double max(double[] d) {
        if (d != null && d.length != 0) {
            double max = 4.9E-324D;

            for (int i = 0; i < d.length; ++i) {
                if (d[i] > max) {
                    max = d[i];
                }
            }

            return max;
        } else {
            throw new NullPointerException("double array is null or length equal zero.");
        }
    }

    public static Double min(double[] d) {
        if (d != null && d.length != 0) {
            double min = 1.7976931348623157E308D;

            for (int i = 0; i < d.length; ++i) {
                if (d[i] < min) {
                    min = d[i];
                }
            }

            return min;
        } else {
            throw new NullPointerException("double array is null or length is equal to zero.");
        }
    }

    public static int max(int[] d) {
        if (d != null && d.length != 0) {
            int max = -2147483648;

            for (int i = 0; i < d.length; ++i) {
                if (d[i] > max) {
                    max = d[i];
                }
            }

            return max;
        } else {
            throw new NullPointerException("double array is null or length is equal to zero.");
        }
    }

    public static int min(int[] d) {
        if (d != null && d.length != 0) {
            int min = 2147483647;

            for (int i = 0; i < d.length; ++i) {
                if (d[i] < min) {
                    min = d[i];
                }
            }

            return min;
        } else {
            throw new NullPointerException("double array is null or length is equal to zero.");
        }
    }

    public static Double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b = new BigDecimal(Double.toString(v));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, 4).doubleValue();
        }
    }

    public static int compare(double v1, double v2) {
        if (v1 > v2) {
            return 1;
        } else {
            return v1 < v2 ? -1 : 0;
        }
    }

    public static int compare(int v1, int v2) {
        return compare((double) v1, (double) v2);
    }

    /*方法二：推荐，速度最快
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static void main(String[] args) {
        try {
            System.out.println(fen2Yuan(100L));
            System.out.println(format(2.5D));
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

}
