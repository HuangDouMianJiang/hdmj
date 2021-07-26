package com.github.hdmj.entity;

import java.io.Serializable;

public class SDKOrderInfoModel implements Serializable {

    // 签名
    private String sign = "";
    //大订单号
    private String bigOrderNo = "";
    // 大订时间
    private String bigOrderTime = "";
    // 用户名
    private String CustomerName = "";

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBigOrderNo() {
        return bigOrderNo;
    }

    public void setBigOrderNo(String bigOrderNo) {
        this.bigOrderNo = bigOrderNo;
    }

    public String getBigOrderTime() {
        return bigOrderTime;
    }

    public void setBigOrderTime(String bigOrderTime) {
        this.bigOrderTime = bigOrderTime;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }
}
