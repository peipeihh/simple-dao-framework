<#function dashedToCamel(s)>
    <#return s
    ?replace('(^_+)|(_+$)', '', 'r')
    ?replace('\\_+(\\w)?', ' $1', 'r')
    ?replace('([A-Z])', ' $1', 'r')
    ?capitalize
    ?replace(' ' , '')
    >
</#function>
<#assign table_camel = dashedToCamel(table)>
<#assign table_cap = table_camel?cap_first>
<#assign table_upper = table?upper_case>
package com.pphh.dfw;

import com.pphh.dfw.table.AbstractTable;
import com.pphh.dfw.table.TableField;


/**
* a table definition which is used to build sql with SqlBuilder
*
* @author ${author}
* @date ${date}
*/
public class ${table_cap}Table extends AbstractTable {

    public static final ${table_cap}Table ${table?upper_case} = new ${table_cap}Table();

    <#list table_fields as x>
    public final TableField ${x} = new TableField("${x}");
    </#list>

    public ${table_cap}Table() {
        super("order");

        <#list table_fields as x>
        this.insertFields(${x});
        </#list>

        <#if table_fields_pk??>
        this.setPkField(${table_fields_pk});
        </#if>
    }

}
