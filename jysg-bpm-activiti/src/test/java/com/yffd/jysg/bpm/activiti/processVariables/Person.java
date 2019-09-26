package com.yffd.jysg.bpm.activiti.processVariables;

import java.io.Serializable;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月16日 下午4:21:49 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class Person implements Serializable {
	private static final long serialVersionUID = -162408209237857580L;
	private Integer id;//编号
	private String name;//姓名
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

