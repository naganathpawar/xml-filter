package com.mef.filter.main.service;

import org.springframework.stereotype.Repository;

/**
 * @author naganathpawar
 *
 */
@Repository
public interface FileReader {
	String createCSVFileForDevice(String path, String deviceId);
	String createCSVFileForPlan(String path, String planIds);
}
