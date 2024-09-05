package com.example.projectservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task_member")
@IdClass(TaskMemberId.class)
@Data
public class TaskMember {
    @Id
    @Column(name ="task_id")
    private Long taskId;
    @Id
    @Column(name = "user_id")
    private Long userId;
}



