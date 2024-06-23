package com.optimainmobiliaria.shared.model.dto.api_response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private Boolean success;
    private String message;
    private T data;
}

