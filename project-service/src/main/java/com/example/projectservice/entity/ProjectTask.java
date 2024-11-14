package com.example.projectservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "project_task")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProjectTask {
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "projectuser_task",
            joinColumns = @JoinColumn(name = "projectuser_id"),
            inverseJoinColumns = @JoinColumn(name = "projecttask_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ProjectUser> projectUsers;

    public void assignToMember(ProjectUser projectUser)
    {
        projectUsers.add(projectUser);
    }


}
