package com.zdaniel.countit.counter.service;

import com.zdaniel.countit.counter.model.dto.CounterDTO;
import com.zdaniel.countit.counter.model.entity.Counter;
import org.springframework.stereotype.Service;

@Service
public class CounterMapperImpl implements CounterMapper {

    @Override
    public Counter toEntity(CounterDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Counter(dto.getName(), dto.getCount());
    }

    @Override
    public CounterDTO toDTO(Counter counter) {
        if (counter == null) {
            return null;
        }

        return new CounterDTO(counter.getName(), counter.getCount());
    }

}
