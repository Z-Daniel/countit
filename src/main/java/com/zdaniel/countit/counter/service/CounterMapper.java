package com.zdaniel.countit.counter.service;

import com.zdaniel.countit.counter.model.dto.CounterDTO;
import com.zdaniel.countit.counter.model.entity.Counter;

public interface CounterMapper {

    Counter toEntity(CounterDTO dto);
    CounterDTO toDTO(Counter counter);

}
