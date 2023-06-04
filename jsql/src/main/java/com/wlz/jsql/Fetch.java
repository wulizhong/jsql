package com.wlz.jsql;

import java.util.List;

public interface Fetch<T> {
    public T fetch();
    public List<T> fetchList();

    public Record fetchPage(int pageNo,int size);
}
