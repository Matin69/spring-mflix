package com.mflix.core.common;

import java.util.List;

public interface SearchRepository<T extends QueryParams, T2> {

    List<T2> query(T t);

    List<T2> search(String searchText);
}
