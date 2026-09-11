package edu.hbuas.campustodo.service;

import edu.hbuas.campustodo.model.Priority;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskServiceTest {

    @Test
    void shouldAddTask() {
        TaskService service = new TaskService();

        var task = service.addTask("完成需求评审");

        assertEquals(1L, task.getId());
        assertEquals("完成需求评审", task.getTitle());
        assertFalse(task.isCompleted());
        assertEquals(1, service.listAll().size());
    }

    @Test
    void shouldRejectBlankTitle() {
        TaskService service = new TaskService();

        assertThrows(IllegalArgumentException.class,
                () -> service.addTask("   "));
    }

    @Test
    void shouldAddTaskWithPriority() {
        TaskService service = new TaskService();

        var task = service.addTask("准备答辩PPT", Priority.HIGH);

        assertEquals("准备答辩PPT", task.getTitle());
        assertEquals(Priority.HIGH, task.getPriority());
    }

    @Test
    void shouldDefaultPriorityToMedium() {
        TaskService service = new TaskService();

        var task = service.addTask("日常复习");

        assertEquals(Priority.MEDIUM, task.getPriority());
    }

    @Test
    void shouldRejectNullPriorityWhenAdding() {
        TaskService service = new TaskService();

        assertThrows(IllegalArgumentException.class,
                () -> service.addTask("任务", null));
    }

    @Test
    void shouldFilterByPriority() {
        TaskService service = new TaskService();
        service.addTask("高优先级任务", Priority.HIGH);
        service.addTask("普通任务");
        service.addTask("低优先级任务", Priority.LOW);

        var highTasks = service.filterByPriority(Priority.HIGH);
        var lowTasks = service.filterByPriority(Priority.LOW);

        assertEquals(1, highTasks.size());
        assertEquals("高优先级任务", highTasks.get(0).getTitle());
        assertEquals(1, lowTasks.size());
        assertEquals("低优先级任务", lowTasks.get(0).getTitle());
    }

    @Test
    void shouldReturnEmptyListWhenNoTaskMatchesPriority() {
        TaskService service = new TaskService();
        service.addTask("普通任务");

        assertTrue(service.filterByPriority(Priority.HIGH).isEmpty());
    }

    @Test
    void shouldRejectNullPriorityWhenFiltering() {
        TaskService service = new TaskService();

        assertThrows(IllegalArgumentException.class,
                () -> service.filterByPriority(null));
    }

    @Test
    void shouldReturnSnapshotFromFilter() {
        TaskService service = new TaskService();
        service.addTask("高优先级任务", Priority.HIGH);

        var highTasks = service.filterByPriority(Priority.HIGH);

        assertThrows(UnsupportedOperationException.class,
                () -> highTasks.add(service.addTask("另一条", Priority.HIGH)));
    }
}
