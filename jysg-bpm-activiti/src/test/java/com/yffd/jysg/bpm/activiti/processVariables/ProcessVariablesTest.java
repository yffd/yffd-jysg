package com.yffd.jysg.bpm.activiti.processVariables;

import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月16日 下午4:07:36 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ProcessVariablesTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/** 部署流程定义（从classpath） */
	@Test
	public void deployProcessDefinitionByClasspathTest() {
		String name = "流程变量";
		String category = "test";
		Deployment deployment = processEngine.getRepositoryService()
					.createDeployment()
					.name(name )
					.category(category)
					.addClasspathResource("diagrams/processVariables.bpmn")	// 从classpath的资源中加载，一次只能加载一个文件
					.addClasspathResource("diagrams/processVariables.png")
					.deploy();
		System.out.println("部署ID：" + deployment.getId());
		System.out.println("部署名称：" + deployment.getName());
	}
	
	/** 启动流程实例 */
	@Test
	public void startProcessInstanceTest() {
		String processDefinitionKey = "processVariables";
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
	
	/** 设置流程变量 */
	@Test
	public void setVariablesTest() {
		/**与任务（正在执行）*/
		TaskService taskService = processEngine.getTaskService();
		//任务ID
		String taskId = "287504";
		/**一：设置流程变量，使用基本数据类型*/
		taskService.setVariableLocal(taskId, "请假天数", 5);	// 与任务ID绑定，作用域：本任务节点可见，其它任务节点不可见
		taskService.setVariable(taskId, "请假日期", new Date());
		taskService.setVariable(taskId, "请假原因", "回家探亲");
		
		/**二：设置流程变量，使用javabean类型*/
		Person p = new Person();
		p.setId(20);
		p.setName("翠花");
		taskService.setVariable(taskId, "人员信息(添加固定版本)", p);
		
		System.out.println("设置流程变量成功！");
	}
	
	/** 获取流程变量 */
	@Test
	public void getVariablesTest() {
		/**与任务（正在执行）*/
		TaskService taskService = processEngine.getTaskService();
		//任务ID
		String taskId = "287504";
		/**一：获取流程变量，使用基本数据类型*/
		Integer days = (Integer) taskService.getVariable(taskId, "请假天数");
		Date date = (Date) taskService.getVariable(taskId, "请假日期");
		String resean = (String) taskService.getVariable(taskId, "请假原因");
		System.out.println("请假天数："+days);
		System.out.println("请假日期："+date);
		System.out.println("请假原因："+resean);
		
		/**二：获取流程变量，使用javabean类型*/
		Person p = (Person)taskService.getVariable(taskId, "人员信息(添加固定版本)");
		System.out.println(p.getId()+"        "+p.getName());
	}
	
	/** 完成任务 */
	@Test
	public void completeMyTaskTest() {
		String taskId = "42502";
		processEngine.getTaskService().complete(taskId);
		System.out.println("完成任务：任务ID："+taskId);
	}
	
	/** 查询流程变量的历史表 */
	@Test
	public void findHistoryProcessVariables() {
		String processInstanceId = "35001";
		String variableName = "请假天数";
		List<HistoricVariableInstance> list = processEngine.getHistoryService()
				.createHistoricVariableInstanceQuery()
				.processInstanceId(processInstanceId)
//				.variableName(variableName )
				.list();
		if (null != list && list.size() > 0) {
			for (HistoricVariableInstance hvi : list) {
				System.out.println(hvi.getId()+"   "+hvi.getProcessInstanceId()+"   "+hvi.getVariableName()+"   "+hvi.getVariableTypeName()+"    "+hvi.getValue());
			}
		}		
	}
}

