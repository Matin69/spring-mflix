package com.mflix.core.common;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface QueryParams {

    Pageable getPagination();

    Sort getQuerySort();
}
