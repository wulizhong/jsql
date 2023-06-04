package com.wlz.jsql.sql;

import com.wlz.jsql.sql.database.Column;
import com.wlz.jsql.sql.fun.Fun2;

public class MySql extends Function{

    public static Fun2 dateFormat(Column param1, String strParam2){
        return new Fun2("DATE_FORMAT",param1,strParam2);
    }
    public static Fun2 dateFormat(String param1, String param2){
        return new Fun2("DATE_FORMAT",param1,param2);
    }

}
