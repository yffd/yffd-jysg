package com.yffd.jysg.bpm.activiti.receiveTask;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月17日 下午2:35:32 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ReceiveTaskTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/** 部署流程定义（从classpath） */
	@Test
	public void deployProcessDefinitionByClasspathTest() {
		String name = "流程接收任务（等待任务）";
		String category = "test";
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("receiveTask.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("receiveTask.png");
		Deployment deployment = processEngine.getRepositoryService()
					.createDeployment()
					.name(name )
					.category(category)
					.addInputStream("receiveTask.bpmn", inputStreamBpmn)//
					.addInputStream("receiveTask.png", inputStreamPng)//
					.deploy();
		System.out.println("部署ID：" + deployment.getId());
		System.out.println("部署名称：" + deployment.getName());
	}
	
	/** 启动流程实例 */
	@Test
	public void startProcessInstanceTest() {
		String processDefinitionKey = "receiveTask";
		ProcessInstance processInstance = processEngine.getRuntimeService()	// 与正在实行的流程实例和执行对象相关的service
				.startProcessInstanceByKey(processDefinitionKey);	//key=xx.bpmn中的id属性值，key值启动，是按最新版本的流程定义启动
		System.out.println("流程实例ID：" + processInstance.getId());
		System.out.println("流程定义ID：" + processInstance.getProcessDefinitionId());
	}
	
	@Test
	public void step1Test() {
		String processInstanceId = "165001";
		String activityId = "receivetask1";
		Execution execution = processEngine.getRuntimeService()
				.createExecutionQuery()
				.processInstanceId(processInstanceId)
				.activityId(activityId)
				.singleResult();
		
		/** 使用流程变量设置当日销售额，用来传递业务参数 */
		processEngine.getRuntimeService()//
						.setVariable(execution.getId(), "当日总销售额", 33333);
		
		/** 向后执行一步，如果流程处于等待状态，使得流程继续执行 */
		processEngine.getRuntimeService()
						.signal(execution.getId());
		
		System.out.println("任务节点执行完成：" + activityId);
				
	}
	
	@Test
	public void step2Test() {
		String processInstanceId = "165001";
		String activityId = "receivetask2";
		Execution execution = processEngine.getRuntimeService()
				.createExecutionQuery()
				.processInstanceId(processInstanceId)
				.activityId(activityId)
				.singleResult();
		
		/**从流程变量中获取汇总当日销售额的值*/
		Integer value = (Integer)processEngine.getRuntimeService()//
						.getVariable(execution.getId(), "当日总销售额");
		
		System.out.println("给老板发送短信：金额是：" + value);
		
		/** 向后执行一步，如果流程处于等待状态，使得流程继续执行 */
		processEngine.getRuntimeService()
						.signal(execution.getId());
		
		System.out.println("任务节点执行完成：" + activityId);
				
	}
	
}

