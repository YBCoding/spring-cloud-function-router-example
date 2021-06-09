package com.ybcoding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybcoding.dto.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class Test {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @org.junit.jupiter.api.Test
    void givenComments_whenCallingRouterToUppercase_thenReturnCommentsInUppercase() throws Exception {

        List<Comment> comments = List.of(
                new Comment(1L, "first comment"),
                new Comment(2L, "second comment")
        );

        List<Comment> expectedComments = List.of(
                new Comment(1L, "FIRST COMMENT"),
                new Comment(2L, "SECOND COMMENT")
        );

        MvcResult result = this.mockMvc.perform(
                post("/functionRouter")
                        .header("spring.cloud.function.definition", "uppercase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comments)))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andDo(response -> log.debug("response is {}", response.getResponse().getContentAsString()))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedComments)))
                .andExpect(status().isOk());
    }

    @org.junit.jupiter.api.Test
    void givenComments_whenCallingUppercase_thenReturnCommentsInUppercase() throws Exception {

        List<Comment> comments = List.of(
                new Comment(1L, "first comment"),
                new Comment(2L, "second comment")
        );

        List<Comment> expectedComments = List.of(
                new Comment(1L, "FIRST COMMENT"),
                new Comment(2L, "SECOND COMMENT")
        );

        MvcResult result = this.mockMvc.perform(
                post("/uppercase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comments)))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andDo(response -> log.debug("response is {}", response.getResponse().getContentAsString()))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedComments)))
                .andExpect(status().isOk());
    }
}
