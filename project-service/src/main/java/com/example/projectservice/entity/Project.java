package com.example.projectservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project")
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProjectTask> projectTasks = new ArrayList<>() ;

    @OneToMany(mappedBy = "project" , fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ProjectUser> projectUsers  = new HashSet<>();

    public void addMemberToProject(ProjectUser user)
    {
        if(projectUsers == null)
        {
            projectUsers = new HashSet<>();
        }

        projectUsers.add(user);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, startDate, endDate, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Project that = (Project) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);

    }
}
