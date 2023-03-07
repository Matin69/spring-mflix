package com.mflix.core.common;

import org.springframework.data.domain.Pageable;

public interface QueryParams {

    Pageable getPagination();
}
