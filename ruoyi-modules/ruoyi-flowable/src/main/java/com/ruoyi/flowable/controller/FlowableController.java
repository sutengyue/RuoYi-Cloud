package com.ruoyi.flowable.controller;

import com.ruoyi.flowable.service.IFlowableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/flowable")
public class FlowableController {

    @Autowired
    private IFlowableService flowableService;

    @GetMapping("/deployment/list")
    public Map<String, Object> getDeploymentList() {
        return flowableService.getDeploymentList();
    }

    @GetMapping("/process/list")
    public Map<String, Object> getProcessList() {
        return flowableService.getProcessList();
    }

    @GetMapping("/instance/list")
    public Map<String, Object> getInstanceList() {
        return flowableService.getInstanceList();
    }

    @GetMapping("/task/list")
    public Map<String, Object> getTaskList() {
        return flowableService.getTaskList();
    }

    @GetMapping("/history/list")
    public Map<String, Object> getHistoryList() {
        return flowableService.getHistoryList();
    }

    @PostMapping("/process/start")
    public Map<String, Object> startProcess(@RequestBody Map<String, Object> params) {
        String processDefinitionKey = (String) params.get("processDefinitionKey");
        return flowableService.startProcess(processDefinitionKey, params);
    }

    @PostMapping("/task/claim")
    public Map<String, Object> claimTask(@RequestBody Map<String, Object> params) {
        String taskId = (String) params.get("taskId");
        String assignee = (String) params.get("assignee");
        return flowableService.claimTask(taskId, assignee);
    }

    @PostMapping("/task/complete")
    public Map<String, Object> completeTask(@RequestBody Map<String, Object> params) {
        String taskId = (String) params.get("taskId");
        return flowableService.completeTask(taskId, params);
    }

    @DeleteMapping("/deployment/{deploymentId}")
    public Map<String, Object> deleteDeployment(@PathVariable String deploymentId) {
        return flowableService.deleteDeployment(deploymentId);
    }

    @PostMapping("/process/suspend")
    public Map<String, Object> suspendProcess(@RequestBody Map<String, Object> params) {
        String processDefinitionId = (String) params.get("processDefinitionId");
        return flowableService.suspendProcess(processDefinitionId);
    }

    @PostMapping("/process/activate")
    public Map<String, Object> activateProcess(@RequestBody Map<String, Object> params) {
        String processDefinitionId = (String) params.get("processDefinitionId");
        return flowableService.activateProcess(processDefinitionId);
    }

    @PostMapping("/instance/terminate")
    public Map<String, Object> terminateInstance(@RequestBody Map<String, Object> params) {
        String processInstanceId = (String) params.get("processInstanceId");
        return flowableService.terminateInstance(processInstanceId);
    }
}