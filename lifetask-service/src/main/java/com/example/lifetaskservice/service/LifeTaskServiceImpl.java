package com.example.lifetaskservice.service;

import com.example.lifetaskservice.dto.LifeTaskDto;
import com.example.lifetaskservice.entity.LifeTask;
import com.example.lifetaskservice.entity.Status;
import com.example.lifetaskservice.exception.TaskNotFoundException;
import com.example.lifetaskservice.mapper.LifeTaskMapper;
import com.example.lifetaskservice.repository.LifeTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LifeTaskServiceImpl implements ILifeTaskService{

    private final LifeTaskRepository lifeTaskRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public List<LifeTask> getLifeTasks() {
        return lifeTaskRepository.findAll().stream()
                .sorted(Comparator.comparing(LifeTask::getStartDate, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }

    @Override
    public List<LifeTask> getLifeTasksByUserId(Long userId) {
        return lifeTaskRepository.findLifeTasksByUserId(userId).orElse(new ArrayList<>());
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

    @Override
    public List<List<LifeTask>> getLifeTaskBetween(Long userId, LocalDate start, LocalDate end) {
        LocalDateTime startDate = start.atStartOfDay();
        LocalDateTime endDate = end.atTime(LocalTime.MAX);

        List<LifeTask> lifeTasks = lifeTaskRepository.findAllByUserIdAndStartDateBetween(userId, startDate, endDate)
                .orElse(new ArrayList<>());

        Map<LocalDate, List<LifeTask>> groupedByDate = lifeTasks.stream()
                .filter(task -> task.getStartDate() != null)
                .collect(Collectors.groupingBy(task -> task.getStartDate().toLocalDate()));
        return groupedByDate.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    @Scheduled(fixedDelay = 300000)
    public void checkTaskIsOverdue() {
        List<LifeTask> overdueTasks = lifeTaskRepository.findByEndDateBeforeAndStatusIn(LocalDateTime.now(), List.of(Status.PENDING,Status.PROCESS))
                .orElse(new ArrayList<>());
        overdueTasks.forEach(task -> task.setStatus(Status.OVERDUE));
        lifeTaskRepository.saveAll(overdueTasks);
        overdueTasks.stream().filter(task -> task.getUserId() != null)
                .forEach(task -> {
                    String destination = "/user/" + task.getUserId() + "/queue/lifetasks";
                    messagingTemplate.convertAndSend(destination, task.getId().toString() + " is overdue");
                });
    }


}
