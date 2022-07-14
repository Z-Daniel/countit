package com.zdaniel.countit.counter.repository;

import com.zdaniel.countit.counter.model.entity.Counter;

import java.util.List;

public interface CounterRepository {

    List<Counter> findAll();
    Counter findByNameOrElseThrow(String name);
    Counter create(Counter counter);
    Counter update(Counter counter);
    void deleteAll();

}
