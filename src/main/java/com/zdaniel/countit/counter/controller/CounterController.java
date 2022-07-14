package com.zdaniel.countit.counter.controller;

import com.zdaniel.countit.counter.model.dto.CounterDTO;
import com.zdaniel.countit.counter.service.CounterService;
import com.zdaniel.countit.exception.UniqueIdentifierAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(CounterController.ROOT_PATH)
public class CounterController {
    static final String ROOT_PATH = "/api/counter";

    private final CounterService counterService;

    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping
    public ResponseEntity<List<CounterDTO>> findAll() {
        return ResponseEntity.ok(counterService.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<CounterDTO> findByName(@PathVariable String name) {
        return ResponseEntity.ok(counterService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<CounterDTO> create(@RequestBody CounterDTO counterDTO, UriComponentsBuilder builder) {
        return createWithPathSegment(counterDTO, builder);
    }

    @PutMapping("/{name}")
    public ResponseEntity<CounterDTO> update(@PathVariable String name, @RequestBody CounterDTO counterDTO, UriComponentsBuilder builder) {
        try {
            return createWithPathSegment(counterDTO, builder);
        } catch(UniqueIdentifierAlreadyExistsException ex) {
            return ResponseEntity.ok(counterService.update(name, counterDTO));
        }
    }

    private ResponseEntity<CounterDTO> createWithPathSegment(CounterDTO counterDTO, UriComponentsBuilder builder) {
        CounterDTO dto = counterService.create(counterDTO);
        UriComponents uriComponents = builder.path(ROOT_PATH).pathSegment(dto.getName()).build();
        return ResponseEntity.created(uriComponents.toUri()).body(dto);
    }

}
