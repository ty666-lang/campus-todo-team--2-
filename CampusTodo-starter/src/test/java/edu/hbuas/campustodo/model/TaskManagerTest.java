package edu.hbuas.campustodo.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    // 测试默认优先级添加
    @Test
    void addTask_WithDefaultPriority_priorityIs2() {
        TaskManager manager = new TaskManager();
        Task task = manager.addTask("整理笔记");
        assertEquals(2, task.getPriority());
        assertFalse(task.isCompleted());
    }

    // 测试指定优先级添加
    @Test
    void addTask_WithGivenPriority_storesPriority() {
        TaskManager manager = new TaskManager();
        manager.addTask("高数作业", 1);
        manager.addTask("取快递", 3);

        List<Task> all = manager.listAll();
        assertEquals(2, all.size());
        assertEquals(1, all.get(0).getPriority());
        assertEquals(3, all.get(1).getPriority());
    }

    // #1 测试按优先级筛选功能
    @Test
    void filterByPriority_returnsMatchedTasks() {
        TaskManager manager = new TaskManager();
        manager.addTask("任务A", 1);
        manager.addTask("任务B", 2);
        manager.addTask("任务C", 1);
        manager.addTask("任务D", 3);

        List<Task> high = manager.filterByPriority(1);
        assertEquals(2, high.size());

        List<Task> mid = manager.filterByPriority(2);
        assertEquals(1, mid.size());

        List<Task> low = manager.filterByPriority(3);
        assertEquals(1, low.size());

        List<Task> none = manager.filterByPriority(99);
        assertTrue(none.isEmpty());
    }

    // #2 测试完成任务：第一次成功，重复完成失败
    @Test
    void completeTask_duplicateComplete_returnsFalse() {
        TaskManager manager = new TaskManager();
        manager.addTask("跑步", 2);

        Optional<Boolean> first = manager.completeTask(1);
        assertTrue(first.isPresent());
        assertTrue(first.get());

        Optional<Boolean> second = manager.completeTask(1);
        assertTrue(second.isPresent());
        assertFalse(second.get());
    }

    // 完成不存在的任务，返回Optional.empty()
    @Test
    void completeTask_taskNotExist_returnsEmpty() {
        TaskManager manager = new TaskManager();
        Optional<Boolean> res = manager.completeTask(999);
        assertTrue(res.isEmpty());
    }

    // 测试非法优先级会抛出异常
    @Test
    void addTask_invalidPriority_throwsException() {
        TaskManager manager = new TaskManager();
        assertThrows(IllegalArgumentException.class, () -> manager.addTask("无效任务", 5));
        assertThrows(IllegalArgumentException.class, () -> manager.addTask("无效任务", 0));
    }
}
