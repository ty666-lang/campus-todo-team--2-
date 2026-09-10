package edu.hbuas.campustodo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskManager {
    private final List<Task> taskList = new ArrayList<>();
    private long nextId = 1;

    // 旧方法：默认优先级2
    public Task addTask(String title){
        Task t = new Task(nextId++, title);
        taskList.add(t);
        return t;
    }

    // 新方法：可以指定优先级
    public Task addTask(String title, int priority){
        Task t = new Task(nextId++, title, priority);
        taskList.add(t);
        return t;
    }

    public List<Task> listAll(){
        return new ArrayList<>(taskList);
    }

    // #1 按优先级筛选
    public List<Task> filterByPriority(int priority){
        return taskList.stream()
            .filter(task -> task.getPriority() == priority)
            .collect(Collectors.toList());
    }

    // #2 完成任务，返回操作结果
    public Optional<Boolean> completeTask(long taskId){
        Optional<Task> opt = taskList.stream()
            .filter(t -> t.getId() == taskId)
            .findFirst();
        if(opt.isEmpty()){
            return Optional.empty(); // 任务不存在
        }
        Task t = opt.get();
        boolean result = t.complete();
        return Optional.of(result);
    }
}
