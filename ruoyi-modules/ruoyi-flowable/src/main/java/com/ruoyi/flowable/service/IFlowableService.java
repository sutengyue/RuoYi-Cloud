package com.ruoyi.flowable.service;

import java.util.Map;

public interface IFlowableService {

    Map<String, Object> getDeploymentList();

    Map<String, Object> getProcessList();

    Map<String, Object> getInstanceList();

    Map<String, Object> getTaskList();

    Map<String, Object> getHistoryList();

    Map<String, Object> startProcess(String processDefinitionKey, Map<String, Object> variables);

    Map<String, Object> claimTask(String taskId, String assignee);

    Map<String, Object> completeTask(String taskId, Map<String, Object> variables);

    Map<String, Object> deleteDeployment(String deploymentId);

    Map<String, Object> suspendProcess(String processDefinitionId);

    Map<String, Object> activateProcess(String processDefinitionId);

    Map<String, Object> terminateInstance(String processInstanceId);
}