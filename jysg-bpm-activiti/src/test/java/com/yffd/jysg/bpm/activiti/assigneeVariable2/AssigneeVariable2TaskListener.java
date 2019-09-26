package com.yffd.jysg.bpm.activiti.assigneeVariable2;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月18日 上午10:58:10 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class AssigneeVariable2TaskListener implements TaskListener {
	private static final long serialVersionUID = 6154205792813277709L;

	@Override
	public void notify(DelegateTask delegateTask) {
		delegateTask.setAssignee("灭绝师太");
	}

}

