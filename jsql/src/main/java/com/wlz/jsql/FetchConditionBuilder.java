package com.wlz.jsql;

import com.wlz.jsql.sql.*;
import com.wlz.jsql.sql.builder.ConditionBuilder;
import com.wlz.jsql.sql.builder.FromBuilder;
import com.wlz.jsql.sql.builder.OrderByBuilder;
import com.wlz.jsql.sql.builder.SqlFragmentBuilder;
import com.wlz.jsql.sql.database.Column;
import com.wlz.jsql.sql.fun.Fun;

import java.util.List;

public class FetchConditionBuilder<T> extends ConditionBuilder implements Fetch<T>{

    private ConditionBuilder conditionBuilder;
//
    private SqlExecutor sqlExecutor;

    private Class<T> clazz;

    public FetchConditionBuilder(SqlExecutor sqlExecutor, Class<T> clazz, FromBuilder fromBuilder) {
        this.sqlExecutor = sqlExecutor;
        this.clazz = clazz;
        this.fromBuilder = fromBuilder;
    }

    private FromBuilder fromBuilder;


    public FetchConditionBuilder<T> where(Column field) {
        conditionBuilder = fromBuilder.where(field);
        return this;
    }

    public FetchConditionBuilder<T> where(SubSql subSql) {
        conditionBuilder =  conditionBuilder = fromBuilder.where(subSql);
        return this;
    }

    public FetchConditionBuilder<T> where(SqlFragmentBuilder sqlBuilder) {
        conditionBuilder =  conditionBuilder = fromBuilder.where(sqlBuilder);
        return this;
    }

    public FetchConditionBuilder<T> where(RawSql rawSql) {
        conditionBuilder =  conditionBuilder = fromBuilder.where(rawSql);
        return this;
    }
    public FetchConditionBuilder<T> where(Fun fun) {
        conditionBuilder =  conditionBuilder = fromBuilder.where(fun);
        return this;
    }

    @Override
    public FetchConditionBuilder<T> and(Column field) {
        conditionBuilder = conditionBuilder.and(field);
        return this;
    }

    @Override
    public FetchConditionBuilder<T> and(RawSql rawSql) {
        conditionBuilder = conditionBuilder.and(rawSql);
        return this;
    }
    @Override
    public FetchConditionBuilder<T> and(Fun fun) {
        conditionBuilder = conditionBuilder.and(fun);
        return this;
    }


    @Override
    public FetchConditionBuilder<T> or(Column field) {
        conditionBuilder = conditionBuilder.and(field);
        return this;
    }

    @Override
    public FetchConditionBuilder<T> or(RawSql rawSql) {
        conditionBuilder = conditionBuilder.and(rawSql);
        return this;
    }

    @Override
    public FetchConditionBuilder<T> and(SubSql subSql) {
        conditionBuilder = conditionBuilder.and(subSql);
        return this;
    }

    @Override
    public FetchConditionBuilder<T> or(SubSql subSql) {
        conditionBuilder = conditionBuilder.and(subSql);
        return this;
    }
    @Override
    public FetchConditionBuilder<T> or(Fun fun) {
        conditionBuilder = conditionBuilder.and(fun);
        return this;
    }
    @Override
    public FetchConditionBuilder<T> and(SqlFragmentBuilder subSqlBuilder) {
        conditionBuilder = conditionBuilder.and(subSqlBuilder);
        return this;
    }

    @Override
    public FetchConditionBuilder<T> or(SqlFragmentBuilder subSqlBuilder) {
        conditionBuilder = conditionBuilder.and(subSqlBuilder);
        return this;
    }

    @Override
    public FetchOrderByBuilder<T> orderBy(Order... orders) {
        OrderByBuilder orderByBuilder = conditionBuilder.orderBy(orders);
        FetchOrderByBuilder<T> fetchOrderByBuilder = new FetchOrderByBuilder<>(sqlExecutor,clazz,orderByBuilder);
        return fetchOrderByBuilder;
    }

    @Override
    public FetchLimit<T> limit(int offset, int rows) {
        return new FetchLimit<T>(offset, rows,sqlExecutor,clazz);
    }

    @Override
    public FetchLimit<T> limit(int offset) {
        return new FetchLimit<T>(offset,sqlExecutor,clazz);
    }

    @Override
    public T fetch() {
        return sqlExecutor.selectOne(conditionBuilder==null?fromBuilder:conditionBuilder,clazz);
    }

    @Override
    public List<T> fetchList() {
        return sqlExecutor.selectList(conditionBuilder==null?fromBuilder:conditionBuilder,clazz);
    }

    @Override
    public Record fetchPage(int pageNo, int size) {
        return sqlExecutor.selectPage(conditionBuilder==null?fromBuilder:conditionBuilder,pageNo,size);
    }
}
