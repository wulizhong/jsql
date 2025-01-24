package com.wlz.jsql;

import com.wlz.jsql.sql.Limit;
import com.wlz.jsql.sql.builder.OrderByBuilder;

import java.util.List;

public class FetchOrderByBuilder<T> extends OrderByBuilder implements Fetch<T>{

    private SqlExecutor sqlExecutor;
    private Class<T> clazz;
    private OrderByBuilder orderByBuilder;

    public FetchOrderByBuilder(SqlExecutor sqlExecutor,Class<T> clazz, OrderByBuilder orderByBuilder) {
        this.sqlExecutor = sqlExecutor;
        this.orderByBuilder = orderByBuilder;
        this.clazz = clazz;
    }

    @Override
    public FetchLimit<T> limit(int offset, int rows) {
        FetchLimit<T> limit = new FetchLimit<T>(offset, rows,sqlExecutor,clazz);
        limit.setPreSqlFragment(orderByBuilder.pre());
        return limit;
    }

    @Override
    public FetchLimit<T> limit(int offset) {
        FetchLimit<T> limit = new FetchLimit<T>(offset,sqlExecutor,clazz);
        limit.setPreSqlFragment(orderByBuilder.pre());
        return limit;
    }

    @Override
    public T fetch() {
        return sqlExecutor.selectOne(orderByBuilder,clazz);
    }

    @Override
    public List<T> fetchList() {
        return sqlExecutor.selectList(orderByBuilder,clazz);
    }

    @Override
    public Record fetchPage(int pageNo, int size) {
        return sqlExecutor.selectPage(orderByBuilder,pageNo,size);
    }
}
