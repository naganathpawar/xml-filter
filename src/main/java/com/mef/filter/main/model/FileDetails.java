package com.mef.filter.main.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author naganathpawar
 *
 */
public class FileDetails {
	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String path;
	@Getter
	@Setter
	private String folderPath;

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
