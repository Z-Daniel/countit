package com.zdaniel.countit.counter.service;

import com.zdaniel.countit.counter.model.dto.CounterDTO;

import java.util.List;

public interface CounterService {

    List<CounterDTO> findAll();
    CounterDTO findByName(String name);
    CounterDTO create(CounterDTO dto);
    CounterDTO incrementCounterByName(String name);

}
