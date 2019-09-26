package com.yffd.jysg.bpm.activiti.start;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月17日 下午2:28:38 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class StartTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/** 部署流程定义（从classpath） */
	@Test
	public void deployProcessDefinitionByClasspathTest() {
		String name = "流程开始-结束";
		String category = "test";
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("start.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("start.png");
		Deployment deployment = processEngine.getRepositoryService()
					.createDeployment()
					.name(name )
					.category(category)
					.addInputStream("start.bpmn", inputStreamBpmn)//
					.addInputStream("start.png", inputStreamPng)//
					.deploy();
		System.out.println("部署ID：" + deployment.getId());
		System.out.println("部署名称：" + deployment.getName());
	}
	
	/** 启动流程实例 */
	@Test
	public void startProcessInstanceTest() {
		String processDefinitionKey = "start";
		ProcessInstance processInstance = processEngine.getRuntimeService()	// 与正在实行的流程实例和执行对象相关的service
				.startProcessInstanceByKey(processDefinitionKey);	//key=xx.bpmn中的id属性值，key值启动，是按最新版本的流程定义启动
		System.out.println("流程实例ID：" + processInstance.getId());
		System.out.println("流程定义ID：" + processInstance.getProcessDefinitionId());
		
		/** 判断流程是否结束，查询正在执行的执行对象表 */
		ProcessInstance rpi = processEngine.getRuntimeService()
						.createProcessInstanceQuery()
						.processInstanceId(processInstance.getId())
						.singleResult();
		//说明流程实例结束了
		if(rpi==null){
			/** 查询历史，获取流程的相关信息 */
			HistoricProcessInstance hpi = processEngine.getHistoryService()
						.createHistoricProcessInstanceQuery()
						.processInstanceId(processInstance.getId())
						.singleResult();
			System.out.println(hpi.getId()+"    "+hpi.getStartTime()+"   "+hpi.getEndTime()+"   "+hpi.getDurationInMillis());
		}
		
	}
	
}

