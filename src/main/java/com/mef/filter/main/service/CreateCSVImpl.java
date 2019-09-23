package com.mef.filter.main.service;

import com.mef.filter.main.config.AppProperties;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author naganathpawar
 *
 */
@Service
public class CreateCSVImpl implements CreateCSV{
	public static final Logger logger = LoggerFactory.getLogger(CreateCSVImpl.class);

	/* (non-Javadoc)
	 * @see com.mef.filter.main.service.CreateCSV#createCSV(java.lang.String, java.lang.String)
	 */
	@Autowired
	private AppProperties appProperties;
	@Override
	public String createCSV(final String path, final String filePath)throws IOException {
		String csvFile = null;
		if (new java.io.File(path + filePath).exists()) {
			Path folderPath = Paths.get(path.concat(filePath));
			Files.delete(folderPath);
		}
		if (new java.io.File(path.concat(appProperties.getBasePath())).exists()) {
			csvFile = path.concat(filePath);
		} else {
			if (new java.io.File(path.concat(appProperties.getBasePath())).mkdir()) {
				csvFile = path.concat(filePath);
			}
		}
		logger.info(csvFile);
		return csvFile;
	}

	/* (non-Javadoc)
	 * @see com.mef.filter.main.service.CreateCSV#writeSCV(java.util.List, java.lang.String)
	 */
	@Override
	public void writeSCV(final List<String[]> dataList, final String csvFile) throws IOException {
		FileWriter outputfile = new FileWriter(csvFile);
		CSVWriter writer = new CSVWriter(outputfile);
		writer.writeAll(dataList);
		writer.close();
	}



}
