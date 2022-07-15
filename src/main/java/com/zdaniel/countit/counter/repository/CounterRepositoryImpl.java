package com.zdaniel.countit.counter.repository;

import com.zdaniel.countit.counter.model.entity.Counter;
import com.zdaniel.countit.exception.EmptyDataException;
import com.zdaniel.countit.exception.UniqueIdentifierAlreadyExistsException;
import com.zdaniel.countit.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CounterRepositoryImpl implements CounterRepository {
    public static final String UNIQUE_COUNTER_NAME_ERROR = "A counter already exists with name: ";
    public static final String COUNTER_NOT_FOUND_BY_NAME_ERROR = "No counter found with name: ";
    public static final String COUNTER_IS_NULL_ERROR = "Counter should not be null";
    public static final String COUNTER_NAME_IS_NULL_ERROR = "Counter name should not be null";

    private final Map<String, Counter> counterStore = new ConcurrentHashMap<>();

    @Override
    public List<Counter> findAll() {
        List<Counter> storeAsList = new ArrayList<>(counterStore.values());
        storeAsList.sort(Comparator.comparing(Counter::getCreatedAt).reversed());
        return storeAsList;
    }

    @Override
    public Counter create(Counter counter) {
        throwExceptionIfCounterIsNull(counter);
        if (findByName(counter.getName()).isPresent()) throw new UniqueIdentifierAlreadyExistsException(UNIQUE_COUNTER_NAME_ERROR + counter.getName());
        counterStore.put(counter.getName(), counter);
        return counter;
    }

    public Counter incrementCounterByName(String name) {
        if (null == name) throw new EmptyDataException(COUNTER_NAME_IS_NULL_ERROR);
        Counter storedCounter = findByNameOrElseThrow(name);
        storedCounter.incrementCount();
        return storedCounter;
    }

    private void throwExceptionIfCounterIsNull(Counter counter) {
        if (null == counter) throw new EmptyDataException(COUNTER_IS_NULL_ERROR);
    }

    @Override
    public void deleteAll() {
        counterStore.clear();
    }

    public Counter findByNameOrElseThrow(String name) {
        return findByName(name).orElseThrow(() -> new ResourceNotFoundException(COUNTER_NOT_FOUND_BY_NAME_ERROR + name));
    }

    private Optional<Counter> findByName(String name) {
        if (name == null) return Optional.empty();
        return counterStore.containsKey(name) ? Optional.of(counterStore.get(name)) : Optional.empty();
    }

}
