package com.wlz.jsql;

import com.wlz.jsql.sql.Limit;

import java.util.List;

public class FetchLimit<T> extends Limit implements Fetch<T>{
    private SqlExecutor sqlExecutor;

    private Class<T> clazz;

    public FetchLimit(int offset, int rows, SqlExecutor sqlExecutor,Class<T> clazz) {
        super(offset, rows);
        this.sqlExecutor = sqlExecutor;
        this.clazz = clazz;
    }

    public FetchLimit(int offset, SqlExecutor sqlExecutor,Class<T> clazz) {
        super(offset);
        this.sqlExecutor = sqlExecutor;
        this.clazz = clazz;
    }

    @Override
    public T fetch() {
        return sqlExecutor.selectOne(this,clazz);
    }

    @Override
    public List<T> fetchList() {
        return sqlExecutor.selectList(this,clazz);
    }

    @Override
    public Record fetchPage(int pageNo, int size) {
        return sqlExecutor.selectPage(this,pageNo,size);
    }
}
