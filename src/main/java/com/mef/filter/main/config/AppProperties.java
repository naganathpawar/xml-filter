package com.mef.filter.main.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
    @Getter
    @Setter
    private String planPath;
    @Getter
    @Setter
    private String devicePath;
    @Getter
    @Setter
    private String basePath;
    @Getter
    @Setter
    private String contain;
    @Getter
    @Setter
    private String validateMSG;
    @Getter
    @Setter
    private String csvCreatingMSG;
    @Getter
    @Setter
    private String download;
    @Getter
    @Setter
    private String commercialBundle;
    @Getter
    @Setter
    private List<String> planHeader=new ArrayList();
    @Getter
    @Setter
    private List<String> deviceHeader=new ArrayList();
    @Getter
    @Setter
    private List<String> planDetails=new ArrayList();
    @Getter
    @Setter
    private List<String> inputType=new ArrayList();


}
