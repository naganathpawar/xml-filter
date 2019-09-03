package com.mef.filter.main.service;
import java.util.ArrayList;
import java.util.List;

import com.mef.filter.main.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mef.filter.main.util.CommonUtility;

/**
 * @author naganathpawar
 *
 */
@Service
public class FileReaderImpl implements FileReader {
	@Autowired
	CreateCSV createCSV;
	@Autowired
	ReadXML readXML;
	@Autowired
	CommonUtility commonUtility;
	@Autowired
	AppProperties appProperties;
	public static final Logger logger = LoggerFactory.getLogger(FileReaderImpl.class);

		/**
	 * @param path
	 * @param deviceId
	 */
	@Override
	public String createCSVFileForDevice(String path, String deviceId) {
		String csvPath = "";
		try {
			List <String[]> dataList = new ArrayList ( );
			dataList.add ( appProperties.getDeviceHeader ( ).toArray ( new String[ appProperties.getDeviceHeader ( ).size ( ) ] ) );
			readXML.getDeviceDataByDeviceId ( path , deviceId ).forEach ( deviceObj ->
					dataList.add ( new String[] { deviceObj.getId ( ) , deviceObj.getPlanId ( ) , deviceObj.getPriceGross ( ) , deviceObj.getPriceNet ( ) , deviceObj.getPriceVAT ( ) , deviceObj.getRecurringCharge ( ) } )
			);
			csvPath = createCSV.createCSV ( commonUtility.getPath ( path ) , appProperties.getDevicePath ( ) );
			createCSV.writeSCV ( dataList , csvPath );

		} catch ( Exception e ) {
			logger.error ( "ERROR:::createCSVFileForDevice::::" , e );
		}
		return csvPath;
	}

	/**
	 * @param path
	 * @param planIds
	 */
	@Override
	public String createCSVFileForPlan(String path, String planIds) {
		String csvFile = null;
		try {
			List < String[] > dataList = new ArrayList ( );
			dataList.add ( appProperties.getPlanHeader ( ).toArray ( new String[ appProperties.getPlanHeader ( ).size ( ) ] ) );
			readXML.getProductDataForCSV ( path , planIds ).forEach ( productObj ->
					dataList.add (
							new String[] {
									productObj.getId ( ) , productObj.getProductLines ( ) , productObj.getBoxPrice ( ) , productObj.getProductAvailability ( ) , productObj.getBundleAvailability ( ) } )
			);
			csvFile = createCSV.createCSV ( commonUtility.getPath ( path ) , appProperties.getPlanPath ( ) );
			createCSV.writeSCV ( dataList , csvFile );
		} catch ( Exception ex ) {
			logger.error ( "ERROR:::createCSVFileForPlan::::" , ex );
		}
		return csvFile;
	}

	
}
