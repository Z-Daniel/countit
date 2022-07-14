package com.zdaniel.countit.counter.service;

import com.zdaniel.countit.counter.model.dto.CounterDTO;
import org.junit.jupiter.api.Test;
import com.zdaniel.countit.counter.model.entity.Counter;

import static org.junit.jupiter.api.Assertions.*;

public class CounterMapperImplTest {

    private CounterMapper counterMapper = new CounterMapperImpl();

    @Test
    public void should_return_null_when_called_with_null() {
        assertNull(counterMapper.toEntity(null));
        assertNull(counterMapper.toDTO(null));
    }

    @Test
    public void should_map_entity_to_dto() {
        Counter entity = new Counter("TEST_COUNTER", 5);

        CounterDTO dto = counterMapper.toDTO(entity);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getCount(), dto.getCount());
    }

    @Test
    public void should_map_dto_to_entity() {
        CounterDTO dto = new CounterDTO("TEST_COUNTER", 5);

        Counter entity = counterMapper.toEntity(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getCount(), entity.getCount());
    }
}
