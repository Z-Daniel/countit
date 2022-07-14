package com.zdaniel.countit.counter.model.dto;

public class CounterDTO {

    private String name;
    private Integer count;

    public CounterDTO() {
    }

    public CounterDTO(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
