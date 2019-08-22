package com.mef.filter.main.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import com.mef.filter.main.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * @author naganathpawar
 *
 */
@Service
public class CommonUtility {
	@Autowired
	AppProperties appProperties;
	public static final Logger logger = LoggerFactory.getLogger(CommonUtility.class);
/**
 * @param filePath
 * @param value
 * @return
 * @throws IOException
 */
public  String  createFile(@NotNull String filePath,@NotNull String value) throws IOException {

	if (new File(filePath).exists()) {
		Path path = Paths.get(filePath);
		Files.delete(path);
	}
		FileWriter writer=new FileWriter(filePath,false);
		PrintWriter printWriter=new PrintWriter(writer);
		printWriter.printf("%s"+"%n", value);
		printWriter.close();
		
		return filePath;
		
	}

/*************************************
 * @param path
 * @param folderName
 * @return
 */
public String createFolder(@NotNull String path, @NotNull String folderName){
	String csvFile = null;
	String folderPath=path.concat(folderName);

	if (new File(folderPath).exists()) {
		deleteDirectoryStream(Paths.get(folderPath));
		if (!new File(folderPath).exists() && new File(folderPath).mkdir()) {
			csvFile = folderPath;
		}
	}else {
		if(new File(folderPath).mkdir())
			csvFile = folderPath;
	}
	return csvFile;
}

/**
 * @param filePath
 * @return
 */
public String getPath(String filePath) {
	String[] strArr =filePath.split("/");
	return filePath.replace(strArr[strArr.length-1], "");
}

/**
 * @param files
 * @return
 */
public List<String> getIds(String files) {
	List<String> filesList = new ArrayList<>();
	String[] str = files.split(",");
	for (String s : str) {
		filesList.add(s);
	}
	return filesList;
}
	void deleteDirectoryStream(Path path) {
		Stream<Path> walk =null;
		try {
			walk= Files.walk(path);
			walk.sorted(Comparator.reverseOrder())
					.map(Path::toFile)
					.forEach(File::delete);
		}catch (IOException e){
			logger.error("deleteDirectoryStream",e);
		}finally {
			walk.close();
		}
	}

	public String getSelectionType(String value){
	if (value.equalsIgnoreCase(appProperties.getCommercialBundle() ))
		return appProperties.getInputType().get(0);
		else
			return appProperties.getInputType().get(1);
	}
}
