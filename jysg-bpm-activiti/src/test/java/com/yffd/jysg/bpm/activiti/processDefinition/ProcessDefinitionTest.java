package com.yffd.jysg.bpm.activiti.processDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月16日 下午2:39:14 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ProcessDefinitionTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/** 部署流程定义（从classpath） */
	@Test
	public void deployProcessDefinitionByClasspathTest() {
		String name = "流程定义";
		String category = "test";
		Deployment deployment = processEngine.getRepositoryService()
					.createDeployment()
					.name(name )
					.category(category)
					.addClasspathResource("diagrams/helloworld.bpmn")	// 从classpath的资源中加载，一次只能加载一个文件
					.addClasspathResource("diagrams/helloworld.png")
					.deploy();
		System.out.println("部署ID：" + deployment.getId());
		System.out.println("部署名称：" + deployment.getName());
	}
	
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
	
	/** 查询流程定义 */
	@Test
	public void findProcessDefinitionTest() {
		List<ProcessDefinition> list = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				/**指定查询条件,where条件*/
//				.deploymentId(deploymentId)	// 使用部署对象ID查询
//				.processDefinitionId(processDefinitionId)	// 使用流程定义ID查询
//				.processDefinitionKey(processDefinitionKey)	// 使用流程定义的key查询
//				.processDefinitionNameLike(processDefinitionNameLike)	// 使用流程定义的名称模糊查询
//				.latestVersion()	// 最新版本流程定义
				/**排序*/
				.orderByProcessDefinitionVersion().asc()// 按照版本的升序排列
//				.orderByProcessDefinitionName().desc()// 按照流程定义的名称降序排列
				
				/**返回的结果集*/
				.list();	// 返回一个集合列表，封装流程定义
//				.singleResult();	// 返回惟一结果集
//				.count();	// 返回结果集数量
//				.listPage(firstResult, maxResults);	// 分页查询
		if (null != list && list.size() > 0) {
			for (ProcessDefinition pd : list) {
				System.out.println("流程定义ID：" + pd.getId());	// 流程定义的key+版本+随机生成数
				System.out.println("流程定义的名称：" + pd.getName());	// 对应helloworld.bpmn文件中的name属性值
				System.out.println("流程定义的key：" + pd.getKey());	// 对应helloworld.bpmn文件中的id属性值
				System.out.println("流程定义的版本：" + pd.getVersion());	// 当流程定义的key值相同的相同下，版本升级，默认1
				System.out.println("资源名称bpmn文件：" + pd.getResourceName());
				System.out.println("资源名称png文件：" + pd.getDiagramResourceName());
				System.out.println("部署对象ID：" + pd.getDeploymentId());
				System.out.println("#######################################################");
			}
		}
	}
	
	/** 删除流程定义 */
	@Test
	public void deleteProcessDefinitionTest() {
		String deploymentId = "72501";
		// 非级联删除，只能删除没有启动的流程，如果流程启动，就会抛出异常
//		processEngine.getRepositoryService()
//				.deleteDeployment(deploymentId);
		
		// 级联删除，不管流程是否启动，都能可以删除
		processEngine.getRepositoryService()
				.deleteDeployment(deploymentId, true);
		
		System.out.println("删除成功！");
	}
	
	/** 查看流程图 */
	@Test
	public void viewPicTest() throws IOException {
		String deploymentId = "100001";
		String resourceName = "diagrams/helloworld.png";
		
		// 获取图片资源名称
		List<String> list = processEngine.getRepositoryService()
				.getDeploymentResourceNames(deploymentId);
		if (null!=list && list.size() > 0) {
			for (String name : list) {
				System.out.println("部署ID【" + deploymentId + "】， 部署资源名称：" + name);
				if(name.indexOf(".bpmn")>=0){
					resourceName = name;
				}
			}
		}
		
		//获取图片的输入流
		InputStream in = processEngine.getRepositoryService()
				.getResourceAsStream(deploymentId, resourceName);
		
		//将图片生成到D盘的目录下
		File file = new File("D:/" + resourceName);
		//将输入流的图片写到D盘下
		FileUtils.copyInputStreamToFile(in, file);
	}

	/** 删除流程定义（删除key相同的所有不同版本的流程定义） */
	@Test
	public void deleteProcessDefinitionByKeyTest(){
		String processDefinitionKey = "receiveTask";
		List<ProcessDefinition> list = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey)
				.list();
		if (null!=list && list.size() > 0) {
			for(ProcessDefinition pd : list) {
				processEngine.getRepositoryService().deleteDeployment(pd.getDeploymentId(), true);
			}
		}
	}
}

