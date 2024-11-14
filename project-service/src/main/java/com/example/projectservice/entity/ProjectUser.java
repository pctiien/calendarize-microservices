package com.example.projectservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "project_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProjectUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id ;

    @Column(name = "user_id",nullable = false)
    private Long userId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "project_id",nullable = false)
    private Project project;

    @JsonIgnore
    @ManyToMany(mappedBy = "projectUsers", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ProjectTask> projectTasks;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "projectuser_role",
            joinColumns = @JoinColumn(name = "projectuser_id"),
            inverseJoinColumns = @JoinColumn(name = "projectrole_id")
    )
    private Set<ProjectRole> projectRoles = new HashSet<>();

    public void addProjectRole(ProjectRole projectRole)
    {
        projectRoles.add(projectRole);
    }
    public ProjectUser(Long userId, Project project) {
        this.userId = userId;
        this.project = project;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProjectUser that = (ProjectUser) obj;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId);
    }
}
