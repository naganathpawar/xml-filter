package com.mef.filter.main.service;

import java.io.IOException;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Repository;

import com.mef.filter.main.model.DevicePrice;
import com.mef.filter.main.model.ProductDetails;

/**
 * @author naganathpawar
 *
 */
@Repository
public interface ReadXML {
	 Element getNodeDetailsById(String filePath,String id,String type) throws DocumentException ;
	 void replaceNode(String data,String filePath,String id,String type) throws DocumentException, IOException;
	 List<com.mef.filter.main.model.FileDetails> createAndGetFileByIdFromCanonical(String filePath,String ids) throws DocumentException, IOException;
	 List<DevicePrice> getDeviceDataByDeviceId(String filePath,String ids) throws DocumentException, IOException ;
	List<ProductDetails> getProductDataForCSV(String filePath,String ids)throws DocumentException,IOException;
}
