package com.example.lifetaskservice.mapper;

import com.example.lifetaskservice.dto.LifeTaskDto;
import com.example.lifetaskservice.entity.LifeTask;

public class LifeTaskMapper {
    public static LifeTask mapToLifeTask(LifeTaskDto dto)
    {
        return LifeTask.builder().name(dto.getName())
                .endDate(dto.getEndDate())
                .startDate(dto.getStartDate())
                .status(dto.getStatus())
                .description(dto.getDescription())
                .userId(dto.getUserId())
                .build();
    }

}
