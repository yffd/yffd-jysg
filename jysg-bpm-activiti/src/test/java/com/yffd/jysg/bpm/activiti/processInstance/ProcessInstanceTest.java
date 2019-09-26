package com.yffd.jysg.bpm.activiti.processInstance;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月16日 下午3:22:55 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ProcessInstanceTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/** 部署流程定义（从zip） */
	@Test
	public void deployProcessDefinitionByZipTest() {
		String name = "流程定义";
		String category = "test";
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in );
		Deployment deployment = processEngine.getRepositoryService()
					.createDeployment()
					.name(name )
					.category(category)
					.addZipInputStream(zipInputStream)
					.deploy();
		System.out.println("部署ID：" + deployment.getId());
		System.out.println("部署名称：" + deployment.getName());
	}
	
	/** 启动流程实例 */
	@Test
	public void startProcessInstanceTest() {
		String processDefinitionKey = "helloworld";
		ProcessInstance processInstance = processEngine.getRuntimeService()	// 与正在实行的流程实例和执行对象相关的service
				.startProcessInstanceByKey(processDefinitionKey);	//key=xx.bpmn中的id属性值，key值启动，是按最新版本的流程定义启动
		System.out.println("流程实例ID：" + processInstance.getId());
		System.out.println("流程定义ID：" + processInstance.getProcessDefinitionId());
	}
	
	/** 查询个人任务 */
	@Test
	public void findMyTaskTest() {
		String assignee = "张三";
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
		String taskId = "272504";
		processEngine.getTaskService().complete(taskId);
		System.out.println("完成任务：任务ID："+taskId);
	}
	
	/** 查询流程状态（判断流程正在执行，还是结束） */
	@Test
	public void isProcessEndTest() {
		String processInstanceId = "20001";
		ProcessInstance pi = processEngine.getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		if (pi == null) {
			System.out.println("流程已经结束");
		} else {
			System.out.println("流程没有结束");
		}		
	}
	
	/** 查询历史流程实例 */
	@Test
	public void findHistoryProcessInstanceTest() {
		List<HistoricProcessInstance> list = processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery()
				.list();
		if (null != list && list.size() > 0) {
			for (HistoricProcessInstance hpi : list) {
				System.out.println(hpi.getId()+"    "+hpi.getProcessDefinitionId()+"    "+hpi.getStartTime()+"    "+hpi.getEndTime()+"     "+hpi.getDurationInMillis());
			}
		}
	}
	
	/** 查询历史任务 */
	@Test
	public void findHistoryTaskTest() {
		String assignee = "张三";
		List<HistoricTaskInstance> list = processEngine.getHistoryService()
				.createHistoricTaskInstanceQuery()
				.taskAssignee(assignee)
				.list();
		if (null != list && list.size() > 0) {
			for (HistoricTaskInstance hti : list) {
				System.out.println(hti.getId()+"    "+hti.getName()+"    "+hti.getProcessInstanceId()+"   "+hti.getStartTime()+"   "+hti.getEndTime()+"   "+hti.getDurationInMillis());
			}
		}
	}
}

