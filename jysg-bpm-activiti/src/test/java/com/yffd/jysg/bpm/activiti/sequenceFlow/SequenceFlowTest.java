package com.yffd.jysg.bpm.activiti.sequenceFlow;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月16日 下午4:48:34 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class SequenceFlowTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/** 部署流程定义（从classpath） */
	@Test
	public void deployProcessDefinitionByClasspathTest() {
		String name = "流程连线";
		String category = "test";
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("sequenceFlow.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("sequenceFlow.png");
		Deployment deployment = processEngine.getRepositoryService()
					.createDeployment()
					.name(name )
					.category(category)
					.addInputStream("sequenceFlow.bpmn", inputStreamBpmn)//
					.addInputStream("sequenceFlow.png", inputStreamPng)//
					.deploy();
		System.out.println("部署ID：" + deployment.getId());
		System.out.println("部署名称：" + deployment.getName());
	}
	
	/** 启动流程实例 */
	@Test
	public void startProcessInstanceTest() {
		String processDefinitionKey = "sequenceFlow";
		ProcessInstance processInstance = processEngine.getRuntimeService()	// 与正在实行的流程实例和执行对象相关的service
				.startProcessInstanceByKey(processDefinitionKey);	//key=xx.bpmn中的id属性值，key值启动，是按最新版本的流程定义启动
		System.out.println("流程实例ID：" + processInstance.getId());
		System.out.println("流程定义ID：" + processInstance.getProcessDefinitionId());
	}
	
	/** 查询个人任务 */
	@Test
	public void findMyTaskTest() {
		String assignee = "王五";
		List<Task> list = processEngine.getTaskService()	// 与正在执行的任务管理相关的service
				.createTaskQuery()	// 创建任务查询对象
				.taskAssignee(assignee) // 指定办理人
				.list();	// 执行查询，并返回结果
		if (null != list && list.size() > 0) {
			for (Task task : list) {
				System.out.println("任务ID：" + task.getId());
				System.out.println("任务名称：" + task.getName());
				System.out.println("任务创建时间：" + task.getCreateTime());
				System.out.println("任务办理人：" + task.getAssignee());
				System.out.println("流程实例ID：" + task.getProcessInstanceId());
				System.out.println("执行对象ID：" + task.getExecutionId());
				System.out.println("流程定义ID：" + task.getProcessDefinitionId());
				System.out.println("#######################################################");
			}
		}
	}
	
	
	/** 完成任务 */
	@Test
	public void completeMyTaskTest() {
		String taskId = "67502";
		//完成任务的同时，设置流程变量
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("message", "不重要");
		processEngine.getTaskService().complete(taskId, variables);
		
//		processEngine.getTaskService().complete(taskId);
		
		System.out.println("完成任务：任务ID："+taskId);
	}
}

