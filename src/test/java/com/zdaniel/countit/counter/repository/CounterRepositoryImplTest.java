package com.zdaniel.countit.counter.repository;

import com.zdaniel.countit.counter.model.entity.Counter;
import com.zdaniel.countit.exception.EmptyDataException;
import com.zdaniel.countit.exception.ResourceNotFoundException;
import com.zdaniel.countit.exception.UniqueIdentifierAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.zdaniel.countit.counter.repository.CounterRepositoryImpl.*;

public class CounterRepositoryImplTest {

    private CounterRepository counterRepository;

    @BeforeEach
    public void init() {
        counterRepository = new CounterRepositoryImpl();
    }

    @Test
    public void store_should_be_empty_by_default() {
        List<Counter> counterList = counterRepository.findAll();

        Assertions.assertEquals(0, counterList.size());
    }

    @Test
    public void should_return_all_created_counter() {
        Counter c1 = new Counter("TEST_COUNTER_1", 0);
        Counter c2 = new Counter("TEST_COUNTER_2", 2);

        counterRepository.create(c1);
        counterRepository.create(c2);

        List<Counter> counterList = counterRepository.findAll();
        Assertions.assertEquals(2, counterList.size());
        Assertions.assertEquals(c1, counterList.get(0));
        Assertions.assertEquals(c2, counterList.get(1));
    }

    @Test
    public void should_throw_exception_when_name_already_exists_in_store() {
        Counter c1 = new Counter("TEST_COUNTER_1", 0);
        Counter c2 = new Counter("TEST_COUNTER_1", 2);

        counterRepository.create(c1);
        Assertions.assertThrows(UniqueIdentifierAlreadyExistsException.class, () -> counterRepository.create(c2));
    }

    @Test
    public void create_should_throw_error_when_null_passed() {
        EmptyDataException ex = Assertions.assertThrows(EmptyDataException.class, () -> counterRepository.create(null));
        Assertions.assertEquals(COUNTER_IS_NULL_ERROR, ex.getMessage());
    }

    @Test
    public void increment_by_name_should_throw_error_when_null_passed() {
        EmptyDataException ex = Assertions.assertThrows(EmptyDataException.class, () -> counterRepository.incrementCounterByName(null));
        Assertions.assertEquals(COUNTER_NAME_IS_NULL_ERROR, ex.getMessage());
    }

    @Test
    public void should_empty_store() {
        Counter counter = new Counter("TEST_COUNTER", 0);
        counterRepository.create(counter);

        counterRepository.deleteAll();
        List<Counter> counterList = counterRepository.findAll();

        Assertions.assertEquals(0, counterList.size());
    }

    @Test
    public void should_find_created_counter_by_name() {
        Counter counterFromClient = new Counter("TEST_COUNTER", 0);
        counterRepository.create(counterFromClient);

        Counter counterFromStore = counterRepository.findByNameOrElseThrow("TEST_COUNTER");
        Assertions.assertEquals(counterFromClient, counterFromStore);
    }

    @Test
    public void should_throw_exception_when_counter_not_found_by_name() {
        String counterName = null;
        ResourceNotFoundException ex = Assertions.assertThrows(ResourceNotFoundException.class, () -> counterRepository.findByNameOrElseThrow(counterName));
        Assertions.assertEquals(COUNTER_NOT_FOUND_BY_NAME_ERROR + counterName, ex.getMessage());
    }
}