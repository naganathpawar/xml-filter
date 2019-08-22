package com.mef.filter.main.model;

/**
 * @author naganathpawar
 *
 */
public class DevicePrice {

	String id;
	String priceNet;
	String priceGross;
	String priceVAT;
	String planId;
	String recurringCharge;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPriceNet() {
		return priceNet;
	}

	public void setPriceNet(String priceNet) {
		this.priceNet = priceNet;
	}

	public String getPriceGross() {
		return priceGross;
	}

	public void setPriceGross(String priceGross) {
		this.priceGross = priceGross;
	}

	public String getPriceVAT() {
		return priceVAT;
	}

	public void setPriceVAT(String priceVAT) {
		this.priceVAT = priceVAT;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getRecurringCharge() {
		return recurringCharge;
	}

	public void setRecurringCharge(String recurringCharge) {
		this.recurringCharge = recurringCharge;
	}

}
