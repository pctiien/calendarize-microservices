package com.example.projectservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_member")
@IdClass(ProjectMemberId.class)
@Data
public class ProjectMember {
    @Id
    @Column(name ="project_id")
    private Long projectId;
    @Id
    @Column(name = "user_id")
    private Long userId;
}



