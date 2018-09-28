package com.pphh.dfw.core.sqlb;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public class SqlConstant {

    public static final SqlKeyWord SELECT = new SqlKeyWord("SELECT");
    public static final SqlKeyWord DELETE = new SqlKeyWord("DELETE");
    public static final SqlKeyWord INSERT = new SqlKeyWord("INSERT");
    public static final SqlKeyWord UPDATE = new SqlKeyWord("UPDATE");

    public static final SqlKeyWord COUNT = new SqlKeyWord("COUNT");
    public static final SqlKeyWord COUNTALL = new SqlKeyWord("COUNT(1)");
    public static final SqlKeyWord COUNTSTAR = new SqlKeyWord("COUNT(*)");
    public static final SqlKeyWord DISTINCT = new SqlKeyWord("DISTINCT");
    public static final SqlKeyWord FROM = new SqlKeyWord("FROM");
    public static final SqlKeyWord WHERE = new SqlKeyWord("WHERE");
    public static final SqlKeyWord INTO = new SqlKeyWord("INTO");
    public static final SqlKeyWord VALUES = new SqlKeyWord("VALUES");
    public static final SqlKeyWord SET = new SqlKeyWord("SET");
    public static final SqlKeyWord AS = new SqlKeyWord("AS");

    public static final SqlKeyWord JOIN = new SqlKeyWord("JOIN");
    public static final SqlKeyWord INNER_JOIN = new SqlKeyWord("INNER JOIN");
    public static final SqlKeyWord FULL_JOIN = new SqlKeyWord("FULL JOIN");
    public static final SqlKeyWord LEFT_JOIN = new SqlKeyWord("LEFT JOIN");
    public static final SqlKeyWord RIGHT_JOIN = new SqlKeyWord("RIGHT JOIN");
    public static final SqlKeyWord CROSS_JOIN = new SqlKeyWord("CROSS JOIN");

    public static final SqlKeyWord ORDER_BY = new SqlKeyWord("ORDER BY");
    public static final SqlKeyWord ASC = new SqlKeyWord("ASC");
    public static final SqlKeyWord DESC = new SqlKeyWord("DESC");
    public static final SqlKeyWord GROUP_BY = new SqlKeyWord("GROUP BY");

    public static final SqlKeyWord AND = new SqlKeyWord("AND");
    public static final SqlKeyWord OR = new SqlKeyWord("OR");
    public static final SqlKeyWord NOT = new SqlKeyWord("NOT");

    public static final SqlKeyWord WITH_NO_LOCK = new SqlKeyWord("WITH NO LOCK");
    public static final SqlKeyWord WITH_LOCK = new SqlKeyWord("WITH LOCK");

    public static final SqlKeyWord LIMIT = new SqlKeyWord("LIMIT");
    public static final SqlKeyWord TOP = new SqlKeyWord("TOP");
    public static final SqlKeyWord OFFSET = new SqlKeyWord("OFFSET");

    public static final SqlKeyWord LBRACKET = new SqlKeyWord("(");
    public static final SqlKeyWord RBRACKET = new SqlKeyWord(")");

    public static final SqlKeyWord COMMA = new SqlKeyWord(",");
    public static final SqlKeyWord SPACE = new SqlKeyWord(" ");
    public static final SqlKeyWord EQUAL = new SqlKeyWord("=");
    public static final SqlKeyWord STAR = new SqlKeyWord("*");
    public static final SqlKeyWord NOTEQUAL = new SqlKeyWord("<>");
    public static final SqlKeyWord GREATER = new SqlKeyWord(">");
    public static final SqlKeyWord GREATEREQUAL = new SqlKeyWord(">=");
    public static final SqlKeyWord LESS = new SqlKeyWord("<");
    public static final SqlKeyWord LESSEQUAL = new SqlKeyWord("<=");
    public static final SqlKeyWord LIKE = new SqlKeyWord("LIKE");
    public static final SqlKeyWord NOTLIKE = new SqlKeyWord("NOT LIKE");
    public static final SqlKeyWord BETWEEN = new SqlKeyWord("BETWEEN");
    public static final SqlKeyWord IN = new SqlKeyWord("IN");
    public static final SqlKeyWord NOTIN = new SqlKeyWord("NOT IN");
    public static final SqlKeyWord ISNULL = new SqlKeyWord("IS NULL");
    public static final SqlKeyWord ISNOTNULL = new SqlKeyWord("IS NOT NULL");
    public static final SqlKeyWord NOTNULL = new SqlKeyWord("NOT NULL");
}
