package com.example.projectservice.entity;

import java.io.Serializable;

public class ProjectMemberId implements Serializable {

    private Long projectId;
    private Long userId;

    public ProjectMemberId() {}

    public ProjectMemberId(Long projectId, Long userId) {
        this.projectId = projectId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectMemberId that = (ProjectMemberId) o;

        if (!projectId.equals(that.projectId)) return false;
        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        int result = projectId.hashCode();
        result = 31 * result + userId.hashCode();
        return result;
    }
}
