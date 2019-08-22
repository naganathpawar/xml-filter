package com.mef.filter.main.model;

import java.util.Objects;

/**
 * @author naganathpawar
 *
 */
public class FileDetails {
	private String name;
	private String path;
	private String folderPath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof FileDetails) {
            return ((FileDetails) o).getName().equals(name) && ((FileDetails) o).name.equals(name);
		}
		return false;
	}

	@Override
	public int hashCode() { // this should return a unique code
		int hash = 3; // this could be anything, but I would chose a prime(e.g. 5, 7, 11 )
		// again, the multiplier could be anything like 59,79,89, any prime
		hash = 89 * hash + Objects.hashCode(this.name);
		return hash;
	}
}
