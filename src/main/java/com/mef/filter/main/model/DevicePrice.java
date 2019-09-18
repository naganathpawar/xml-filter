package com.mef.filter.main.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author naganathpawar
 *
 */
public class DevicePrice {

	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private String priceNet;
	@Getter
	@Setter
	private String priceGross;
	@Getter
	@Setter
	private String priceVAT;
	@Getter
	@Setter
	private String planId;
	@Getter
	@Setter
	private String recurringCharge;

}
