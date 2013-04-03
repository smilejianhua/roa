/** 
 * Copyright (c) 2012-2015 Wonders Information Co.,Ltd. All Rights Reserved.
 * 5/F 1600 Nanjing RD(W), Shanghai 200040, P.R.C 
 *
 * This software is the confidential and proprietary information of Wonders Group.
 * (Public Service Division / Research & Development Center). You shall not disclose such
 * Confidential Information and shall use it only in accordance with 
 * the terms of the license agreement you entered into with Wonders Group. 
 *
 * Distributable under GNU LGPL license by gun.org
 */
package com.wondersgroup.roa.context.model;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.*;


import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "address")
public class Addresss {

	@XmlAttribute
	@Pattern(regexp = "\\w{4,30}")
	private String zoneCode;

	@XmlAttribute
	private String doorCode;

	@XmlElementWrapper(name = "streets")
	@XmlElement(name = "street")
	private List<Street> streets;

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getDoorCode() {
		return doorCode;
	}

	public void setDoorCode(String doorCode) {
		this.doorCode = doorCode;
	}

	public List<Street> getStreets() {
		return streets;
	}

	public void setStreets(List<Street> streets) {
		this.streets = streets;
	}
}
