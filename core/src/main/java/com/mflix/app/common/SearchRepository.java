package com.mflix.app.common;

import java.util.List;

public interface SearchRepository<T extends QueryParams, T2> {

    List<T2> search(T t);
}
