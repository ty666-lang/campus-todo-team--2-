package edu.hbuas.campustodo.service;

import edu.hbuas.campustodo.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务应用服务。学生将在功能分支中逐步扩展该类。
 */
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1;

    public Task addTask(String title) {
        Task task = new Task(nextId++, title);
        tasks.add(task);
        return task;
    }

    public List<Task> listAll() {
        return List.copyOf(tasks);
    }

    /**
     * 根据id完成任务
     * @param id 任务编号
     * @throws IllegalArgumentException id不存在
     * @throws IllegalStateException 任务已经完成，禁止重复完成
     */
    public void completeTask(long id) {
        Task target = null;
        for (Task t : tasks) {
            if (t.getId() == id) {
                target = t;
                break;
            }
        }
        if (target == null) {
            throw new IllegalArgumentException("任务id不存在：" + id);
        }
        if (target.isCompleted()) {
            throw new IllegalStateException("任务已经完成，不可重复完成, id=" + id);
        }
        target.setCompleted(true);
    }
}
