package com.zdaniel.countit.counter.model.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    private String name;
    private final AtomicInteger count = new AtomicInteger(0);
    private final LocalDateTime createdAt = LocalDateTime.now();

    public Counter() {
    }

    public Counter(String name, Integer count) {
        this.name = name;
        this.count.set(count);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count.get();
    }

    public void incrementCount() {
        count.incrementAndGet();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Counter counter = (Counter) o;
        return Objects.equals(name, counter.name) && Objects.equals(count, counter.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, count);
    }
}
