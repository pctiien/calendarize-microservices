package com.example.projectservice.entity;

import java.io.Serializable;

public class TaskMemberId implements Serializable {

    private Long taskId;
    private Long userId;

    public TaskMemberId() {}

    public TaskMemberId(Long taskId, Long userId) {
        this.taskId = taskId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskMemberId that = (TaskMemberId) o;

        if (!taskId.equals(that.taskId)) return false;
        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        int result = taskId.hashCode();
        result = 31 * result + userId.hashCode();
        return result;
    }
}
