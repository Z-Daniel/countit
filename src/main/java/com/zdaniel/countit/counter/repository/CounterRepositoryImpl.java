package com.zdaniel.countit.counter.repository;

import com.zdaniel.countit.counter.model.entity.Counter;
import com.zdaniel.countit.exception.EmptyDataException;
import com.zdaniel.countit.exception.UniqueIdentifierAlreadyExistsException;
import com.zdaniel.countit.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CounterRepositoryImpl implements CounterRepository {
    public static final String UNIQUE_COUNTER_NAME_ERROR = "A counter already exists with name: ";
    public static final String COUNTER_NOT_FOUND_BY_NAME_ERROR = "No counter found with name: ";
    public static final String COUNTER_IS_NULL_ERROR = "Counter should not be null";

    private Map<String, Counter> counterStore = new HashMap<>();

    @Override
    public List<Counter> findAll() {
        return new ArrayList<>(counterStore.values());
    }

    @Override
    public Counter create(Counter counter) {
        throwErrorIfCounterIsNull(counter);
        if (findByName(counter.getName()).isPresent()) throw new UniqueIdentifierAlreadyExistsException(UNIQUE_COUNTER_NAME_ERROR + counter.getName());
        counterStore.put(counter.getName(), counter);
        return counter;
    }

    @Override
    public Counter update(Counter counter) {
        throwErrorIfCounterIsNull(counter);
        Counter storedCounter = findByNameOrElseThrow(counter.getName());
        storedCounter.setCount(counter.getCount());
        return storedCounter;
    }

    private void throwErrorIfCounterIsNull(Counter counter) {
        if (null == counter) throw new EmptyDataException(COUNTER_IS_NULL_ERROR);
    }

    @Override
    public void deleteAll() {
        counterStore = new HashMap<>();
    }

    public Counter findByNameOrElseThrow(String name) {
        return findByName(name).orElseThrow(() -> new ResourceNotFoundException(COUNTER_NOT_FOUND_BY_NAME_ERROR + name));
    }

    private Optional<Counter> findByName(String name) {
        return counterStore.containsKey(name) ? Optional.of(counterStore.get(name)) : Optional.empty();
    }

}
