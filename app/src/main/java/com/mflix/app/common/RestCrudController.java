package com.mflix.app.common;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestCrudController<T1, T2, ID> {

    default ResponseEntity<RestResponse<List<T1>>> search() {
        return null;
    }

    default ResponseEntity<RestResponse<T1>> findById(ID id) {
        return null;
    }

    default ResponseEntity<RestResponse<T1>> deleteById(ID id) {
        return null;
    }

    default ResponseEntity<RestResponse<T1>> save(T2 saveRequest) {
        return null;
    }
}
