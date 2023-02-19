package com.mflix.app.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractQueryParams implements QueryParams {

    private Integer page;

    private Integer size;

    private String sort;

    @Override
    public Pageable getPagination() {
        return PageRequest.of(page, size, getQuerySort());
    }

    @Override
    public Sort getQuerySort() {
        if (sort == null) {
            return Sort.unsorted();
        }
        String[] unparsedOrders = sort.split(",");
        return Arrays.stream(unparsedOrders)
                .map(unparsedOrder -> unparsedOrder.split(":"))
                .map(unparsedOrderProperties -> {
                    String sortFieldProperty = unparsedOrderProperties[0].toLowerCase();
                    String lowerCaseDirectionProperty = unparsedOrderProperties[1].toLowerCase();
                    if (lowerCaseDirectionProperty.equals("asc")) {
                        return Sort.Order.asc(sortFieldProperty);
                    } else if (lowerCaseDirectionProperty.equals("desc")) {
                        return Sort.Order.desc(sortFieldProperty);
                    } else {
                        throw new RuntimeException("Sort direction of field: " + sortFieldProperty + " is wrong");
                    }
                })
                .collect(Collectors.collectingAndThen(Collectors.toList(), Sort::by));
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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
