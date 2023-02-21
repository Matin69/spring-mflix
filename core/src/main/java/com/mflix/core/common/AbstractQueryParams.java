package com.mflix.core.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class AbstractQueryParams implements QueryParams {

    private final static int DEFAULT_PAGE = 0;

    private final static int DEFAULT_PAGE_SIZE = 10;

    private Integer page;

    private Integer size;

    private String sort;

    @Override
    public Pageable getPagination() {
        return PageRequest.of(getPage(), getSize(), obtainSort());
    }

    public Sort obtainSort() {
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
        return (page != null) ? page : DEFAULT_PAGE;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return (size != null) ? size : DEFAULT_PAGE_SIZE;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
