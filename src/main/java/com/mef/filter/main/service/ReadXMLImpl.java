package com.mef.filter.main.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.mef.filter.main.config.AppProperties;
import com.mef.filter.main.model.FileDetails;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mef.filter.main.model.DevicePrice;
import com.mef.filter.main.model.ProductDetails;
import com.mef.filter.main.util.CommonUtility;

/**
 * @author naganathpawar
 *
 */

@Service
public class ReadXMLImpl implements ReadXML {
	public static final Logger logger = LoggerFactory.getLogger(ReadXMLImpl.class);
	@Autowired
	CommonUtility commonUtility;
	@Autowired
	AppProperties appProperties;

	/**
	 * @param filePath
	 * @param id
	 * @param type
	 * @return Element
	 * @throws DocumentException
	 */
	@Override
	public Element getNodeDetailsById(String filePath, String id, String type) throws DocumentException {

		File inputFile = new File(filePath);
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputFile);
		removeAllNamespaces(document);
		String query = commonUtility.getSelectionType(type).concat(appProperties.getContain().concat( id ).concat( "')]"));
		logger.info(query);

		return (Element) document.selectObject(query);
	}

	/**
	 * @param data
	 * @param filePath
	 * @param id
	 * @param type
	 * @throws DocumentException,IOException
	 */
	@Override
	public void replaceNode(String data, String filePath, String id, String type)
			throws DocumentException, IOException {

		List<Node> nodes = getNode(data, filePath);
		if (!nodes.isEmpty()) {
			File inputFile = new File(filePath);
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputFile);
			removeAllNamespaces(document);
			Element root = (Element) document
					.selectObject(commonUtility.getSelectionType(type).concat(appProperties.getContain().concat( id + "')]")));

			for (int temp = 0; temp < nodes.size(); temp++) {
				Node nNode = nodes.get(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					root.setContent(eElement.content());
				}
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			XMLWriter writer = new XMLWriter(new FileOutputStream(filePath), format);
			writer.write(document);
			writer.close();
		}

	}

	/**
	 * @param data
	 * @param filePath
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	private List<Node> getNode(String data, String filePath) throws DocumentException, IOException {
		String[] strArr = filePath.split("/");
		String path = filePath.replace(strArr[strArr.length - 1], "");
		File inputFile = new File(createFile(path + "demo.xml", data));
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputFile);

		return document.content();
	}

	/**
	 * @param filePath
	 * @param value
	 * @return
	 * @throws IOException
	 */
	private String createFile(String filePath, String value) throws IOException {
		if (new File(filePath).exists()) {
			Path path = Paths.get(filePath);
			Files.delete(path);
		}
		FileWriter writer = new FileWriter(filePath, false);
		PrintWriter printWriter = new PrintWriter(writer);
		printWriter.printf("%s" + "%n", value);
		printWriter.close();

		return filePath;

	}

	/**
	 * @param doc
	 */
	private void removeAllNamespaces(Document doc) {
		Element root = doc.getRootElement();
		if (root.getNamespace() != Namespace.NO_NAMESPACE) {
			removeNamespaces(root.content());
		}
	}

	/**
	 * @param doc
	 * @param original
	 */
	@SuppressWarnings("unused")
	private void unfixNamespaces(Document doc, Namespace original) {
		Element root = doc.getRootElement();
		if (original != null) {
			setNamespaces(root.content(), original);
		}
	}

	private void setNamespace(Element elem, Namespace ns) {

		elem.setQName(QName.get(elem.getName(), ns, elem.getQualifiedName()));
	}

	/**
	 * Recursively removes the namespace of the element and all its children: sets
	 * to Namespace.NO_NAMESPACE
	 */
	@SuppressWarnings("unused")
	private void removeNamespaces(Element elem) {
		setNamespaces(elem, Namespace.NO_NAMESPACE);
	}

	/**
	 * Recursively removes the namespace of the list and all its children: sets to
	 * Namespace.NO_NAMESPACE
	 */
	private void removeNamespaces(List<?> l) {
		setNamespaces(l, Namespace.NO_NAMESPACE);
	}

	/**
	 * Recursively sets the namespace of the element and all its children.
	 */
	/**
	 * @param elem
	 * @param ns
	 */
	private void setNamespaces(Element elem, Namespace ns) {
		setNamespace(elem, ns);
		setNamespaces(elem.content(), ns);
	}

	/**
	 * Recursively sets the namespace of the List and all children if the current
	 * namespace is match
	 */
	/**
	 * @param l
	 * @param ns
	 */
	private void setNamespaces(List<?> l, Namespace ns) {
		Node n = null;
		for (int i = 0; i < l.size(); i++) {
			n = (Node) l.get(i);

			if (n.getNodeType() == Node.ATTRIBUTE_NODE) {
				((Attribute) n).setNamespace(ns);
			}
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				setNamespaces((Element) n, ns);
			}
		}
	}

	/**
	 * @param filePath
	 * @param ids
	 * @return com.bean.File
	 * @throws DocumentException
	 * @throws IOException
	 */
	@Override
	public List<com.mef.filter.main.model.FileDetails> createAndGetFileByIdFromCanonical(String filePath, String ids)
			throws DocumentException, IOException {
		List<FileDetails> files = new ArrayList();
		File inputFile = new File(filePath);
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputFile);
		String newFolder = commonUtility.createFolder(commonUtility.getPath(filePath), "BundleFolder");
		removeAllNamespaces(document);
		for (String id : commonUtility.getIds(ids)) {
			files.addAll(getFileDetails(newFolder, id, document));
		}
		return files;
	}

	/**
	 * @param folderName
	 * @param id
	 * @param document
	 * @return List<com.bean.File>
	 */
	private List<FileDetails> getFileDetails(String folderName, String id, Document document) {

		List<FileDetails> files = new ArrayList<>();
		appProperties.getInputType().forEach(v -> {
			String xmlFileName;
			if(v.contains("commercialProductList"))
				xmlFileName="commercialProduct";
			else
				xmlFileName="commercialBundle";
			Node node = document.selectSingleNode(v+appProperties.getContain()+id+ "')]");
			if (node != null && node.hasContent()) {
				try {
					commonUtility.createFile(folderName.concat("/").concat(xmlFileName+"-"+id).concat(".xml"), node.asXML());
				} catch (IOException e) {
					logger.error("getFileDetails",e);
				}
				FileDetails file = new com.mef.filter.main.model.FileDetails();
				file.setName(xmlFileName+"-"+id + ".xml");
				file.setPath(folderName .concat("/" ).concat(xmlFileName+"-"+id.concat(".xml")));
				file.setFolderPath(folderName);
				files.add(file);
			}

		});

		return files;
	}

	/**
	 * @param filePath
	 * @param ids
	 * @throws DocumentException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DevicePrice> getDeviceDataByDeviceId(String filePath, String ids)
			throws DocumentException, IOException {
		File inputFile = new File(filePath);
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputFile);
		List<DevicePrice> list = new ArrayList();
		removeAllNamespaces(document);
		for (String id : commonUtility.getIds(ids)) {
			List<Node> node = document.selectNodes(appProperties.getInputType().get(0)
					.concat("[contains(deviceSpecificPricing/devicePrice/id,'" .concat(id .concat( "')]"))));
			for (Node n : node) {
				String planId = null;
				String recurringCharge = null;
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) n;
					planId = eElement.elementText("id");
					recurringCharge = eElement.elementText("recurringCharge");
				}
				List<Node> devicePriceList = n
						.selectNodes("deviceSpecificPricing/devicePrice".concat(appProperties.getContain()).concat( id ).concat( "')]"));
				for (Node ns : devicePriceList) {
					DevicePrice devicePrice = new DevicePrice();
					if (ns.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) ns;
						devicePrice.setId(id);
						devicePrice.setPriceGross(eElement.elementText("priceGross"));
						devicePrice.setPriceNet(eElement.elementText("priceNet"));
						devicePrice.setPriceVAT(eElement.elementText("priceVAT"));
						devicePrice.setPlanId(planId);
						devicePrice.setRecurringCharge(recurringCharge);
						list.add(devicePrice);
					}
				}
			}
		}
		return list;
	}

	 /**
     * @param filePath
     * @param ids
     * @throws DocumentException
     * @throws IOException
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductDetails> getProductDataForCSV(String filePath, String ids) throws DocumentException,IOException {
		File inputFile = new File(filePath);
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputFile);
		List<ProductDetails> productDetailsList = new ArrayList();
		removeAllNamespaces(document);
		for (String id : commonUtility.getIds(ids)) {
			List<Node> node = document.selectNodes(appProperties.getInputType().get(1).concat(appProperties.getContain()).concat( id) .concat("')]"));
			for (Node n : node) {
				ProductDetails productDetails=new ProductDetails();
				Node boxPrice = n.selectSingleNode("boxPrice");
				productDetails.setBoxPrice(boxPrice!=null?boxPrice.asXML():"");
					Node productLines = n.selectSingleNode("productLines");
					productDetails.setProductLines(productLines!=null?productLines.asXML():"");
					Node productAvailability = n.selectSingleNode("productAvailability");
					productDetails.setProductAvailability(productAvailability!=null?productAvailability.asXML():"");
					Node bundleAvailability = n.selectSingleNode("bundleAvailability");
					productDetails.setBundleAvailability(bundleAvailability!=null?bundleAvailability.asXML():"");
					productDetails.setId(id);
					productDetailsList.add(productDetails);
			}
		}
		return productDetailsList;
	}

}
