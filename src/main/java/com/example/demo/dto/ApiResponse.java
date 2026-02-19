package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private int status;
    private String message;
    private Object data;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    // Atajos para respuestas comunes
    public static ApiResponse success(String message, Object data) {
        return ApiResponse.builder()
                .status(200)
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResponse error(int status, String message) {
        return ApiResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}