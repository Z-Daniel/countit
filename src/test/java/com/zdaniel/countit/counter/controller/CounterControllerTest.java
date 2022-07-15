package com.zdaniel.countit.counter.controller;

import com.zdaniel.countit.counter.repository.CounterRepository;
import com.zdaniel.countit.counter.model.dto.CounterDTO;
import com.zdaniel.countit.counter.model.entity.Counter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static com.zdaniel.countit.counter.repository.CounterRepositoryImpl.COUNTER_NOT_FOUND_BY_NAME_ERROR;
import static com.zdaniel.countit.counter.repository.CounterRepositoryImpl.UNIQUE_COUNTER_NAME_ERROR;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CounterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CounterRepository counterRepository;

    private CounterDTO dto;

    @BeforeEach
    void init() {
        dto = new CounterDTO("TEST_COUNTER", 0);
        counterRepository.deleteAll();
    }

    @Test
    public void givenDTO_whenCreate_thenReturnSavedDTO() throws Exception {
        ResultActions response = sendPostRequest(dto);

        expectResponseBodyMatchingWithDTO(response, status().isCreated());
    }

    @Test
    public void givenDTO_whenCreateTwice_ThenReturnErrorMessageAndBadRequest() throws Exception {
        sendPostRequest(dto);
        ResultActions response = sendPostRequest(dto);

        response.andDo(print()).
                andExpect(status().isBadRequest())
                .andExpect(content().string(UNIQUE_COUNTER_NAME_ERROR + dto.getName()));
    }

    @Test
    public void givenAlreadyExistingEntity_whenUpdate_ThenReturnSavedDTOWithOk() throws Exception {
        Counter counter = new Counter("TEST_COUNTER", 0);
        counterRepository.create(counter);
        dto.setCount(1);

        ResultActions response = mockMvc.perform(put(CounterController.ROOT_PATH + "/" + counter.getName()));

        expectResponseBodyMatchingWithDTO(response, status().isOk());
    }

    @Test
    public void givenEmptyStore_whenIncrementCounterByName_ThenReturnError() throws Exception {
        String counterName = "TEST_COUNTER";
        mockMvc.perform(put(CounterController.ROOT_PATH + "/" + counterName))
                .andExpect(status().isNotFound())
                .andExpect(content().string(COUNTER_NOT_FOUND_BY_NAME_ERROR + counterName));
    }

    @Test
    public void givenEmptyStore_whenFindByName_ThenReturnNotFound() throws Exception {
        ResultActions response = mockMvc.perform(get(CounterController.ROOT_PATH + "/TEST_COUNTER"));

        response.andDo(print()).andExpect(status().isNotFound()).andExpect(content().string(COUNTER_NOT_FOUND_BY_NAME_ERROR + dto.getName()));
    }

    @Test
    public void givenStoreWithEntity_whenFindByName_ThenReturnDTO() throws Exception {
        Counter counter = new Counter("TEST_COUNTER", 0);
        counterRepository.create(counter);

        ResultActions response = mockMvc.perform(get(CounterController.ROOT_PATH + "/" + counter.getName()));

        expectResponseBodyMatchingWithDTO(response, status().isOk());
    }

    @Test
    public void givenStoreWithEntities_whenFindAll_ThenReturnAllAsDTOs() throws Exception {
        Counter counter = new Counter("TEST_COUNTER", 0);
        counterRepository.create(counter);

        ResultActions response = mockMvc.perform(get(CounterController.ROOT_PATH + "/" + counter.getName()));

        expectResponseBodyMatchingWithDTO(response, status().isOk());
    }

    private void expectResponseBodyMatchingWithDTO(ResultActions response, ResultMatcher statusResultMatcher) throws Exception {
        response.andDo(print()).
                andExpect(statusResultMatcher)
                .andExpect(jsonPath("$.name",
                        is(dto.getName())))
                .andExpect(jsonPath("$.count",
                        is(dto.getCount())));
    }

    private ResultActions sendPostRequest(CounterDTO dto) throws Exception {
        return mockMvc.perform(post(CounterController.ROOT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
    }


}
