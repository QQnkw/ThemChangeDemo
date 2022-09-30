package com.them.change.lib.entity;

public class SkinAttr {

	/**
	 * name of the attr, ex: background or textSize or textColor
	 */
	public String attrName;
	
	/**
	 * id of the attr value refered to, normally is [2130745655]
	 */
	public int attrValueRefId;
	
	/**
	 * entry name of the value , such as [app_exit_btn_background]
	 */
	public String attrValueRefName;
	
	/**
	 * type of the value , such as color or drawable
	 */
	public String attrValueTypeName;

	@Override
	public String toString() {
		return "SkinAttr \n[\nattrName=" + attrName + ", \n"
				+ "attrValueRefId=" + attrValueRefId + ", \n"
				+ "attrValueRefName=" + attrValueRefName + ", \n"
				+ "attrValueTypeName=" + attrValueTypeName
				+ "\n]";
	}
}
