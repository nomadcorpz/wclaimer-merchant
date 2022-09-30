package com.cenmetrix.wclaimermerchant.Models;

import java.io.Serializable;
import java.text.DateFormat;

public class WarrantyDetailModel implements Serializable {
    String prod_SerialNo = "";
    String prod_ModelNo = "";
    String prod_ModelName = "";
    int prod_WarrantyPeriod = -1;
    String prod_WarrantyAddedDate = "";
    boolean prod_WarrantyIsStarted = false;
    String prod_WarrantyStartedDate = "";
    String prod_Merchant = "";
    String prod_Owner = "";
    String prod_WarrantyConditions = "";
    String prod_Remark = "";

    public WarrantyDetailModel() {
    }

    public WarrantyDetailModel(String prod_SerialNo, String prod_ModelNo, String prod_ModelName, int prod_WarrantyPeriod, String prod_WarrantyAddedDate, boolean prod_WarrantyIsStarted, String prod_WarrantyStartedDate, String prod_Merchant, String prod_Owner, String prod_WarrantyConditions, String prod_Remark) {
        this.prod_SerialNo = prod_SerialNo;
        this.prod_ModelNo = prod_ModelNo;
        this.prod_ModelName = prod_ModelName;
        this.prod_WarrantyPeriod = prod_WarrantyPeriod;
        this.prod_WarrantyAddedDate = prod_WarrantyAddedDate;
        this.prod_WarrantyIsStarted = prod_WarrantyIsStarted;
        this.prod_WarrantyStartedDate = prod_WarrantyStartedDate;
        this.prod_Merchant = prod_Merchant;
        this.prod_Owner = prod_Owner;
        this.prod_WarrantyConditions = prod_WarrantyConditions;
        this.prod_Remark = prod_Remark;
    }

    public String getProd_SerialNo() {
        return prod_SerialNo;
    }

    public void setProd_SerialNo(String prod_SerialNo) {
        this.prod_SerialNo = prod_SerialNo;
    }

    public String getProd_ModelNo() {
        return prod_ModelNo;
    }

    public void setProd_ModelNo(String prod_ModelNo) {
        this.prod_ModelNo = prod_ModelNo;
    }

    public String getProd_ModelName() {
        return prod_ModelName;
    }

    public void setProd_ModelName(String prod_ModelName) {
        this.prod_ModelName = prod_ModelName;
    }

    public int getProd_WarrantyPeriod() {
        return prod_WarrantyPeriod;
    }

    public void setProd_WarrantyPeriod(int prod_WarrantyPeriod) {
        this.prod_WarrantyPeriod = prod_WarrantyPeriod;
    }

    public String getProd_WarrantyAddedDate() {
        return prod_WarrantyAddedDate;
    }

    public void setProd_WarrantyAddedDate(String prod_WarrantyAddedDate) {
        this.prod_WarrantyAddedDate = prod_WarrantyAddedDate;
    }

    public boolean isProd_WarrantyIsStarted() {
        return prod_WarrantyIsStarted;
    }

    public void setProd_WarrantyIsStarted(boolean prod_WarrantyIsStarted) {
        this.prod_WarrantyIsStarted = prod_WarrantyIsStarted;
    }

    public String getProd_WarrantyStartedDate() {
        return prod_WarrantyStartedDate;
    }

    public void setProd_WarrantyStartedDate(String prod_WarrantyStartedDate) {
        this.prod_WarrantyStartedDate = prod_WarrantyStartedDate;
    }

    public String getProd_Merchant() {
        return prod_Merchant;
    }

    public void setProd_Merchant(String prod_Merchant) {
        this.prod_Merchant = prod_Merchant;
    }

    public String getProd_Owner() {
        return prod_Owner;
    }

    public void setProd_Owner(String prod_Owner) {
        this.prod_Owner = prod_Owner;
    }

    public String getProd_WarrantyConditions() {
        return prod_WarrantyConditions;
    }

    public void setProd_WarrantyConditions(String prod_WarrantyConditions) {
        this.prod_WarrantyConditions = prod_WarrantyConditions;
    }

    public String getProd_Remark() {
        return prod_Remark;
    }

    public void setProd_Remark(String prod_Remark) {
        this.prod_Remark = prod_Remark;
    }
}
