package com.mflix.app.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

public abstract class AbstractQueryParams implements QueryParams {

    private Integer page;

    private Integer size;

    @Override
    public Pageable getPagination() {
        return PageRequest.of(page, size);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = Objects.requireNonNullElse(page, 0);
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = Objects.requireNonNullElse(size, 10);
    }
}
