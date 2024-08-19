package com.example.lifetaskservice.service;

import com.example.lifetaskservice.dto.LifeTaskDto;
import com.example.lifetaskservice.entity.LifeTask;
import com.example.lifetaskservice.entity.Status;
import com.example.lifetaskservice.exception.TaskNotFoundException;
import com.example.lifetaskservice.mapper.LifeTaskMapper;
import com.example.lifetaskservice.repository.LifeTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LifeTaskServiceImpl implements ILifeTaskService{

    private final LifeTaskRepository lifeTaskRepository;
    @Override
    public List<LifeTask> getLifeTasks() {
        return lifeTaskRepository.findAll().stream()
                .sorted(Comparator.comparing(LifeTask::getStartDate, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }

    @Override
    public LifeTask createLifeTask(LifeTaskDto dto) {
        LifeTask lifeTask = LifeTaskMapper.mapToLifeTask(dto);
        lifeTaskRepository.save(lifeTask);
        return lifeTask;
    }

    @Override
    public LifeTask doneTask(Long id) {
        LifeTask lifeTask = lifeTaskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("id",id.toString()));

        lifeTask.setStatus(Status.COMPLETED);
        lifeTaskRepository.save(lifeTask);
        return lifeTask;
    }

    @Override
    public LifeTask updateTask(LifeTask lifeTask) {
        LifeTask lifeTaskOptional = lifeTaskRepository.findById(lifeTask.getId())
                .orElseThrow(()-> new TaskNotFoundException("id",lifeTask.getId().toString()));
        lifeTaskRepository.save(lifeTask);
        return lifeTask;
    }
}
