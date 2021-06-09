package com.ybcoding;

import com.ybcoding.dto.Comment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application {

    @Bean
    public Function<List<Comment>, List<Comment>> uppercase() {
        return comments -> comments.stream()
                .map(c -> new Comment(c.getId(), c.getMessage().toUpperCase()))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}