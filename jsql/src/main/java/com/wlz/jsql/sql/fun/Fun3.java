package com.wlz.jsql.sql.fun;

import com.wlz.jsql.sql.database.Column;

public class Fun3 extends Fun{
    private String functionName;

    private Column columnParam1;
    private Column columnParam2;
    private Column columnParam3;

    private String strParam1;

    private String strParam2;
    private String strParam3;

    public Fun3(String functionName, Column columnParam1, Column columnParam2, Column columnParam3) {
        this.functionName = functionName;
        this.columnParam1 = columnParam1;
        this.columnParam2 = columnParam2;
        this.columnParam3 = columnParam3;
    }

    public Fun3(String functionName, Column columnParam1, Column columnParam2, String strParam3) {
        this.functionName = functionName;
        this.columnParam1 = columnParam1;
        this.columnParam2 = columnParam2;
        this.strParam3 = strParam3;
    }
    public Fun3(String functionName, Column columnParam1,String strParam2, String strParam3) {
        this.functionName = functionName;
        this.columnParam1 = columnParam1;
        this.strParam2 = strParam2;
        this.strParam3 = strParam3;
    }
    public Fun3(String functionName, String strParam1,String strParam2, String strParam3) {
        this.functionName = functionName;
        this.strParam1 = strParam1;
        this.strParam2 = strParam2;
        this.strParam3 = strParam3;
    }

    public Fun3(String functionName, String strParam1,String strParam2, Column columnParam3) {
        this.functionName = functionName;
        this.strParam1 = strParam1;
        this.strParam2 = strParam2;
        this.columnParam3 = columnParam3;
    }

    public Fun3(String functionName, String strParam1, Column columnParam2, Column columnParam3) {
        this.functionName = functionName;
        this.strParam1 = strParam1;
        this.columnParam2 = columnParam2;
        this.columnParam3 = columnParam3;
    }

    @Override
    public String toSql() {
        StringBuffer sb = new StringBuffer(" "+functionName+" ");

        sb.append("(");

        if(columnParam1!=null&&columnParam2!=null&&columnParam3!=null){

            sb.append(columnParam1.getName());
            sb.append(",");
            sb.append(columnParam2.getName());
            sb.append(",");
            sb.append(columnParam3.getName());

        }else if(columnParam1!=null&&columnParam2!=null&&strParam3!=null){
            sb.append(columnParam1.getName());
            sb.append(",");
            sb.append(columnParam2.getName());
            sb.append(",");
            sb.append(strParam3);
        }else if(columnParam1!=null&&strParam2!=null&&strParam3!=null){
            sb.append(columnParam1.getName());
            sb.append(",");
            sb.append(strParam2);
            sb.append(",");
            sb.append(strParam3);
        }else if(strParam1!=null&&strParam2!=null&&strParam3!=null){
            sb.append(strParam1);
            sb.append(",");
            sb.append(strParam2);
            sb.append(",");
            sb.append(strParam3);
        }else if(strParam1!=null&&strParam2!=null&&columnParam3!=null){
            sb.append(strParam1);
            sb.append(",");
            sb.append(strParam2);
            sb.append(",");
            sb.append(columnParam3.getName());
        }else if(strParam1!=null&&columnParam2!=null&&columnParam3!=null){
            sb.append(strParam1);
            sb.append(",");
            sb.append(columnParam2.getName());
            sb.append(",");
            sb.append(columnParam3.getName());
        }
        sb.append(")");
        if(eqColumn!=null){
            sb.append("  = "+eqColumn.getName());
        }else if(eqStr!=null){
            sb.append("  = "+eqStr);
        }else if(gtColumn!=null){
            sb.append("  > "+gtColumn.getName());
        }else if(gtStr!=null){
            sb.append("  > "+gtStr);
        }else if(gteColumn!=null){
            sb.append("  >= "+gteColumn.getName());
        }else if(gteStr!=null){
            sb.append("  >= "+gteStr);
        }else if(ltColumn!=null){
            sb.append("  < "+ltColumn.getName());
        }else if(ltStr!=null){
            sb.append("  < "+ltStr);
        }else if(lteColumn!=null){
            sb.append("  <= "+lteColumn.getName());
        }else if(lteStr!=null){
            sb.append("  <= "+lteStr);
        }else if(alias!=null){
            sb.append(" as "+alias);
        }else if(columnParam1!=null){
            sb.append(" as "+columnParam1.getName());
        }

        return sb.toString();
    }
}
