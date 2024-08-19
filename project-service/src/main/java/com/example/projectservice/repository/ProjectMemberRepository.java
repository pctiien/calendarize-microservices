package com.example.projectservice.repository;

import com.example.projectservice.entity.ProjectMember;
import com.example.projectservice.entity.ProjectMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {
    Optional<List<ProjectMember>> findAllByUserId(Long userId);
}
