package com.optimainmobiliaria.shared.model.dto.page_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {

    private int pageNumber;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private int pageSize;
    private List<T> content;
}

