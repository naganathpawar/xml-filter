package com.mef.filter.main.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String planPath;
    private String devicePath;
    private String basePath;
    private String contain;
    private String validateMSG;
    private String csvCreatingMSG;
    private String download;
    private String commercialBundle;
    private List<String> planHeader=new ArrayList();
    private List<String> deviceHeader=new ArrayList();
    private List<String> planDetails=new ArrayList();
    private List<String> inputType=new ArrayList();

    public String getPlanPath() {
        return planPath;
    }

    public void setPlanPath(String planPath) {
        this.planPath = planPath;
    }

    public String getDevicePath() {
        return devicePath;
    }

    public void setDevicePath(String devicePath) {
        this.devicePath = devicePath;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getContain() {
        return contain;
    }

    public void setContain(String contain) {
        this.contain = contain;
    }

    public String getValidateMSG() {
        return validateMSG;
    }

    public void setValidateMSG(String validateMSG) {
        this.validateMSG = validateMSG;
    }

    public String getCsvCreatingMSG() {
        return csvCreatingMSG;
    }

    public void setCsvCreatingMSG(String csvCreatingMSG) {
        this.csvCreatingMSG = csvCreatingMSG;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getCommercialBundle() {
        return commercialBundle;
    }

    public void setCommercialBundle(String commercialBundle) {
        this.commercialBundle = commercialBundle;
    }

    public List<String> getPlanHeader() {
        return planHeader;
    }

    public void setPlanHeader(List<String> planHeader) {
        this.planHeader = planHeader;
    }

    public List<String> getDeviceHeader() {
        return deviceHeader;
    }

    public void setDeviceHeader(List<String> deviceHeader) {
        this.deviceHeader = deviceHeader;
    }

    public List<String> getPlanDetails() {
        return planDetails;
    }

    public void setPlanDetails(List<String> planDetails) {
        this.planDetails = planDetails;
    }

    public List<String> getInputType() {
        return inputType;
    }

    public void setInputType(List<String> inputType) {
        this.inputType = inputType;
    }
}
