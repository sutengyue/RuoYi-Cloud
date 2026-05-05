package com.ruoyi.flowable.service.impl;

import com.ruoyi.flowable.service.IFlowableService;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlowableServiceImpl implements IFlowableService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Override
    public Map<String, Object> getDeploymentList() {
        Map<String, Object> result = new HashMap<>();
        List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Deployment deployment : deployments) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", deployment.getId());
            item.put("name", deployment.getName());
            item.put("category", deployment.getCategory());
            item.put("deployTime", deployment.getDeploymentTime());
            list.add(item);
        }
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", list);
        result.put("total", list.size());
        return result;
    }

    @Override
    public Map<String, Object> getProcessList() {
        Map<String, Object> result = new HashMap<>();
        List<ProcessDefinition> processes = repositoryService.createProcessDefinitionQuery().list();
        List<Map<String, Object>> list = new ArrayList<>();
        for (ProcessDefinition process : processes) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", process.getId());
            item.put("name", process.getName());
            item.put("key", process.getKey());
            item.put("version", process.getVersion());
            item.put("deploymentId", process.getDeploymentId());
            item.put("suspended", process.isSuspended());
            list.add(item);
        }
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", list);
        result.put("total", list.size());
        return result;
    }

    @Override
    public Map<String, Object> getInstanceList() {
        Map<String, Object> result = new HashMap<>();
        List<ProcessInstance> instances = runtimeService.createProcessInstanceQuery().list();
        List<Map<String, Object>> list = new ArrayList<>();
        for (ProcessInstance instance : instances) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", instance.getId());
            item.put("processDefinitionId", instance.getProcessDefinitionId());
            item.put("businessKey", instance.getBusinessKey());
            item.put("startTime", instance.getStartTime());
            item.put("ended", instance.isEnded());
            list.add(item);
        }
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", list);
        result.put("total", list.size());
        return result;
    }

    @Override
    public Map<String, Object> getTaskList() {
        Map<String, Object> result = new HashMap<>();
        List<Task> tasks = taskService.createTaskQuery().list();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Task task : tasks) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", task.getId());
            item.put("name", task.getName());
            item.put("assignee", task.getAssignee());
            item.put("processInstanceId", task.getProcessInstanceId());
            item.put("createTime", task.getCreateTime());
            item.put("priority", task.getPriority());
            list.add(item);
        }
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", list);
        result.put("total", list.size());
        return result;
    }

    @Override
    public Map<String, Object> getHistoryList() {
        Map<String, Object> result = new HashMap<>();
        List<HistoricProcessInstance> histories = historyService.createHistoricProcessInstanceQuery().finished().list();
        List<Map<String, Object>> list = new ArrayList<>();
        for (HistoricProcessInstance history : histories) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", history.getId());
            item.put("processDefinitionId", history.getProcessDefinitionId());
            item.put("businessKey", history.getBusinessKey());
            item.put("startTime", history.getStartTime());
            item.put("endTime", history.getEndTime());
            item.put("duration", history.getDurationInMillis());
            list.add(item);
        }
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", list);
        result.put("total", list.size());
        return result;
    }

    @Override
    public Map<String, Object> startProcess(String processDefinitionKey, Map<String, Object> variables) {
        Map<String, Object> result = new HashMap<>();
        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
            result.put("code", 200);
            result.put("msg", "流程启动成功");
            result.put("processInstanceId", processInstance.getId());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "流程启动失败: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, Object> claimTask(String taskId, String assignee) {
        Map<String, Object> result = new HashMap<>();
        try {
            taskService.claim(taskId, assignee);
            result.put("code", 200);
            result.put("msg", "任务认领成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "任务认领失败: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, Object> completeTask(String taskId, Map<String, Object> variables) {
        Map<String, Object> result = new HashMap<>();
        try {
            taskService.complete(taskId, variables);
            result.put("code", 200);
            result.put("msg", "任务完成成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "任务完成失败: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, Object> deleteDeployment(String deploymentId) {
        Map<String, Object> result = new HashMap<>();
        try {
            repositoryService.deleteDeployment(deploymentId, true);
            result.put("code", 200);
            result.put("msg", "部署删除成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "部署删除失败: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, Object> suspendProcess(String processDefinitionId) {
        Map<String, Object> result = new HashMap<>();
        try {
            repositoryService.suspendProcessDefinitionById(processDefinitionId);
            result.put("code", 200);
            result.put("msg", "流程挂起成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "流程挂起失败: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, Object> activateProcess(String processDefinitionId) {
        Map<String, Object> result = new HashMap<>();
        try {
            repositoryService.activateProcessDefinitionById(processDefinitionId);
            result.put("code", 200);
            result.put("msg", "流程激活成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "流程激活失败: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, Object> terminateInstance(String processInstanceId) {
        Map<String, Object> result = new HashMap<>();
        try {
            runtimeService.deleteProcessInstance(processInstanceId, "终止");
            result.put("code", 200);
            result.put("msg", "流程终止成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "流程终止失败: " + e.getMessage());
        }
        return result;
    }
}