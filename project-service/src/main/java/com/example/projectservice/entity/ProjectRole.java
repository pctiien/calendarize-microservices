package com.example.projectservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "project_role")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id ;

    @Enumerated(EnumType.STRING)
    private ProjectRoleName name;

    @ManyToMany(fetch =  FetchType.EAGER)
    @JoinTable(name = "projectrole_permission",
            joinColumns = @JoinColumn(name = "projectrole_id"),
            inverseJoinColumns = @JoinColumn(name = "projectpermission_id")
    )
    @JsonIgnore
    private Set<ProjectPermission> permissions;

    public ProjectRole(ProjectRoleName projectRoleName) {
        this.name = projectRoleName;
    }
}
