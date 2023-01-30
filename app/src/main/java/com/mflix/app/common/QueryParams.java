package com.mflix.app.common;

import org.springframework.data.domain.Pageable;

public interface QueryParams {

    Pageable getPagination();
}
