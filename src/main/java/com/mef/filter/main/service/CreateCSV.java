package com.mef.filter.main.service;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

/**
 * @author naganathpawar
 *
 */
@Repository
public interface CreateCSV {
	String createCSV(final String path, final String filePath)throws IOException ;

	void writeSCV(final List<String[]> dataList, final String csvFile) throws IOException;

}
