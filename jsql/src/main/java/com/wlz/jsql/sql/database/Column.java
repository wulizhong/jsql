package com.wlz.jsql.sql.database;

import java.lang.reflect.Array;
import java.util.*;

import com.wlz.jsql.sql.SqlFragment;
import com.wlz.jsql.sql.builder.ConditionBuilder;
import com.wlz.jsql.util.CollectionUtils;
import com.wlz.jsql.util.StringUtils;

public class Column extends SqlFragment {

    private String tableAlias;

    private String name;

    private Object value;

    private int operator = 0;

    private final int EQ = 1;

    private final int NOT_EQ = 2;

    private final int IN = 3;

    private final int NOT_IN = 4;

    private final int LIKE = 5;

    private final int GREATER_THAN = 6;

    private final int GREATER_THAN_EQUAL = 7;

    private final int LESS_THAN = 8;

    private final int LESS_THAN_EQUAL = 9;

    private final int IS_NULL = 10;

    private final int IS_NOT_NULL = 11;

    private int type;

    public Column() {

    }

    public Column(String tableAlias, String name) {
        this.tableAlias = tableAlias;
        this.name = name;
    }

    public Column(String tableAlias, String name, int type) {
        this.tableAlias = tableAlias;
        this.name = name;
        this.type = type;
    }

    public Column eq(Object value) {

        Column field = new Column(tableAlias, name);

        field.value = value;

        field.operator = EQ;

        return field;
    }

    public Column notEqual(Object value) {

        Column field = new Column(tableAlias, name);

        field.value = value;

        field.operator = NOT_EQ;

        return field;
    }

    public Column neq(Object value) {
        return notEqual(value);
    }

    public Column in(Object values) {
        Column field = new Column(tableAlias, name);
        field.value = values;
        field.operator = IN;
        return field;
    }

    public Column notIn(Object values) {
        Column field = new Column(tableAlias, name);
        field.value = values;
        field.operator = NOT_IN;
        return field;
    }

    public Column like(String value) {
        Column field = new Column(tableAlias, name);
        field.value = value;
        field.operator = LIKE;
        return field;
    }

    public ConditionBuilder and(Column field) {
        ConditionBuilder conditionBuilder = new ConditionBuilder();
        conditionBuilder.and(field);
        conditionBuilder.setPreSqlFragment(this);
        return conditionBuilder;
    }

    public ConditionBuilder or(Column field) {
        ConditionBuilder conditionBuilder = new ConditionBuilder();
        conditionBuilder.or(field);
        conditionBuilder.setPreSqlFragment(this);
        return conditionBuilder;
    }

    //>
    public Column greaterTan(Object value) {
        Column field = new Column(tableAlias, name);
        field.value = value;
        field.operator = GREATER_THAN;
        return field;
    }

    public Column gt(Object value) {
        return greaterTan(value);
    }

    public Column greaterTanAndEqual(Object value) {
        Column field = new Column(tableAlias, name);
        field.value = value;
        field.operator = GREATER_THAN_EQUAL;
        return field;
    }

    public Column gte(Object value) {
        return greaterTanAndEqual(value);
    }

    public Column lessThan(Object value) {
        Column field = new Column(tableAlias, name);
        field.value = value;
        field.operator = LESS_THAN;
        return field;
    }

    public Column lt(Object value) {
        return lessThan(value);
    }

    //<=
    public Column lessThanAndEqual(Object value) {
        Column field = new Column(tableAlias, name);
        field.value = value;
        field.operator = LESS_THAN_EQUAL;
        return field;
    }

    public Column lte(Object value) {
        return lessThanAndEqual(value);
    }


    public Column isNull() {
        Column field = new Column(tableAlias, name);
        field.operator = IS_NULL;
        return field;
    }

    public Column isNotNull() {
        Column field = new Column(tableAlias, name);
        field.operator = IS_NOT_NULL;
        return field;
    }

    @Override
    public List<Object> paramters() {
        // TODO Auto-generated method stub
        LinkedList<Object> parameterList = new LinkedList<>();

        if (operator != LIKE && operator != IN && operator != NOT_IN) {
            if (value instanceof SqlFragment) {
                SqlFragment fragment = (SqlFragment) value;
                if (CollectionUtils.isNotEmpty(fragment.paramters())) {
                    parameterList.addAll(fragment.paramters());
                }
            } else if (value != null) {
                parameterList.add(value);
            }
        }

        SqlFragment preSqlFragment = pre();
        if (preSqlFragment != null) {
            if (CollectionUtils.isNotEmpty(preSqlFragment.paramters())) {
                ListIterator<Object> it = preSqlFragment.paramters().listIterator();
                while (it.hasPrevious()) {
                    parameterList.addFirst(it.previous());
                }
            }
        }
        return parameterList;
    }

    @Override
    public String toSql() {
        // TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer(" ");
        sb.append(this.tableAlias);
        sb.append(".");
        sb.append(name);
        switch (operator) {
            case EQ:
                sb.append(" = ");
                break;
            case NOT_EQ:
                sb.append(" != ");
                break;
            case LIKE:
                sb.append(" like ");
                break;
            case IN:
                sb.append(" in ");
                break;
            case NOT_IN:
                sb.append(" not in ");
                break;
            case GREATER_THAN:
                sb.append(" > ");
                break;
            case GREATER_THAN_EQUAL:
                sb.append(" >= ");
                break;
            case LESS_THAN:
                sb.append(" < ");
                break;
            case LESS_THAN_EQUAL:
                sb.append(" <= ");
                break;
            case IS_NULL:
                sb.append(" is null ");
                break;
            case IS_NOT_NULL:
                sb.append(" is not null ");
                break;

            default:
                break;
        }
        if (value != null && value instanceof SqlFragment) {
            SqlFragment fragment = (SqlFragment) value;
            sb.append(fragment.toSql());
        } else if ((operator == LIKE || operator == IN || operator == NOT_IN) && value != null) {

            if (operator == LIKE) {
                sb.append(" " + value + " ");
            } else if (value.getClass().isArray() || value instanceof Iterable) {
                List list = null;
                if (value.getClass().isArray()) {
                    list = new ArrayList();
                    int length = Array.getLength(value);
                    for (int i = 0; i < length; i++) {
                        list.add(Array.get(value, i));
                    }
                } else if (value instanceof Iterable) {
                    if (value instanceof List) {
                        list = (List) value;
                    } else {
                        list = new ArrayList();
                        Iterable iterable = (Iterable) value;
                        Iterator iterator = iterable.iterator();
                        while(iterator.hasNext()){
                            list.add(iterator.next());
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(list)) {
                    Object obj = list.get(0);
                    if (obj instanceof String) {
                        sb.append(" ('" + StringUtils.join(list, "','") + "') ");
                    } else {
                        sb.append(" (" + StringUtils.join(list, ",") + ") ");
                    }
                }


            } else {
                if (value instanceof String) {
                    sb.append(" ('" + value + "') ");
                } else {
                    sb.append(" (" + value + ") ");
                }
            }

        } else if (value != null) {
            sb.append(" ? ");
        }

        SqlFragment preSqlFragment = pre();
        if (preSqlFragment != null) {
            sb.insert(0, pre().toSql());
        }
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }


}
