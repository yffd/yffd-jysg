package com.yffd.jysg.bpm.activiti.processHistory;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.Test;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月16日 下午4:31:49 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ProcessHistoryTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/** 查询历史流程实例 */
	@Test
	public void findHistoryProcessInstanceTest() {
		String processInstanceId = "35001";
		HistoricProcessInstance hpi = processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		if (null != hpi) {
			System.out.println(hpi.getId()+"    "+hpi.getProcessDefinitionId()+"    "+hpi.getStartTime()+"    "+hpi.getEndTime()+"     "+hpi.getDurationInMillis());
		}
	}
	
	/** 查询历史任务 */
	@Test
	public void findHistoryTaskTest() {
		String processInstanceId = "35001";
		List<HistoricTaskInstance> list = processEngine.getHistoryService()
						.createHistoricTaskInstanceQuery()
						.processInstanceId(processInstanceId)//
						.list();
		if (null != list && list.size() > 0) {
			for (HistoricTaskInstance hti : list) {
				System.out.println(hti.getId()+"    "+hti.getName()+"    "+hti.getProcessInstanceId()+"   "+hti.getStartTime()+"   "+hti.getEndTime()+"   "+hti.getDurationInMillis());
			}
		}
	}
	
	/** 查询历史活动 */
	@Test
	public void findHistoryActivityTest() {
		String processInstanceId = "35001";
		List<HistoricActivityInstance> list = processEngine.getHistoryService()//
						.createHistoricActivityInstanceQuery()//创建历史活动实例的查询
						.processInstanceId(processInstanceId)//
						.list();
		if (null != list && list.size() > 0) {
			for (HistoricActivityInstance hai : list) {
				System.out.println(hai.getId()+"   "+hai.getProcessInstanceId()+"   "+hai.getActivityType()+"  "+hai.getStartTime()+"   "+hai.getEndTime()+"   "+hai.getDurationInMillis());
			}
		}
	}
}

