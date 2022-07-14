package com.zdaniel.countit.counter.service;

import com.zdaniel.countit.counter.repository.CounterRepository;
import com.zdaniel.countit.counter.model.dto.CounterDTO;
import com.zdaniel.countit.counter.model.entity.Counter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CounterServiceImpl implements CounterService {

    private final CounterRepository counterRepository;
    private final CounterMapper counterMapper;

    public CounterServiceImpl(CounterRepository counterRepository, CounterMapper counterMapper) {
        this.counterRepository = counterRepository;
        this.counterMapper = counterMapper;
    }

    @Override
    public List<CounterDTO> findAll() {
        return counterRepository.findAll().stream().map(counterMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CounterDTO findByName(String name) {
        return counterMapper.toDTO(counterRepository.findByNameOrElseThrow(name));
    }

    @Override
    public CounterDTO create(CounterDTO dto) {
        Counter counter = counterMapper.toEntity(dto);
        return counterMapper.toDTO(counterRepository.create(counter));
    }

    @Override
    public CounterDTO update(String name, CounterDTO dto) {
        dto.setName(name);
        Counter counter = counterMapper.toEntity(dto);
        return counterMapper.toDTO(counterRepository.update(counter));
    }

}
