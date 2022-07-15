package com.zdaniel.countit.counter.service;

import com.zdaniel.countit.counter.model.dto.CounterDTO;
import com.zdaniel.countit.counter.repository.CounterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import com.zdaniel.countit.counter.model.entity.Counter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CounterServiceImplTest {

    @Mock
    private CounterRepository counterRepository;
    @Mock
    private CounterMapper counterMapper;
    @InjectMocks
    private CounterServiceImpl counterService;

    @Test
    public void should_return_mapped_dto_list() {
        Counter entity = new Counter("TEST_COUNTER", 5);
        CounterDTO dto = new CounterDTO("TEST_COUNTER", 5);

        when(counterRepository.findAll()).thenReturn(List.of(entity));
        when(counterMapper.toDTO(entity)).thenReturn(dto);

        List<CounterDTO> dtos = counterService.findAll();

        assertEquals(1, dtos.size());
        assertEquals(dto, dtos.get(0));
    }

    @Test
    public void should_return_mapped_dto_queried_by_name() {
        String counterName = "TEST_COUNTER";
        Counter entity = new Counter(counterName, 5);
        CounterDTO expected = new CounterDTO(counterName, 5);

        when(counterRepository.findByNameOrElseThrow(counterName)).thenReturn(entity);
        when(counterMapper.toDTO(entity)).thenReturn(expected);

        CounterDTO actual = counterService.findByName(counterName);

        assertEquals(expected, actual);
    }

    @Test
    public void create_should_map_dto_to_entity_save_and_map_result_to_dto() {
        String counterName = "TEST_COUNTER";
        Counter entity = new Counter(counterName, 5);
        CounterDTO expected = new CounterDTO(counterName, 5);

        when(counterMapper.toEntity(expected)).thenReturn(entity);
        when(counterRepository.create(entity)).thenReturn(entity);
        when(counterMapper.toDTO(entity)).thenReturn(expected);

        CounterDTO actual = counterService.create(expected);

        assertEquals(expected, actual);
    }

    @Test
    public void increment_counter_by_name() {
        String counterName = "TEST_COUNTER";
        Counter entity = new Counter(counterName, 5);
        CounterDTO expected = new CounterDTO(counterName, 5);

        when(counterRepository.incrementCounterByName("TEST_COUNTER")).thenReturn(entity);
        when(counterMapper.toDTO(entity)).thenReturn(expected);

        CounterDTO actual = counterService.incrementCounterByName(counterName);

        assertEquals(expected, actual);
    }
}
