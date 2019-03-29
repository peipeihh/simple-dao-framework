<#assign table_cap = table?cap_first>
<#assign table_upper = table?upper_case>
package com.pphh.dfw.table;

import com.pphh.dfw.table.AbstractTable;
import com.pphh.dfw.table.TableField;


/**
* a table definition which is useful to build sql with SqlBuilder
*
* @author ${author}
* @date ${date}
*/
public class ${table_cap}Table extends AbstractTable {

    public static final ${table_cap}Table ${table?upper_case} = new ${table_cap}Table();

    <#list ["id", "name", "city_id", "country_id"] as x>
    public final TableField ${x} = new TableField("${x}");
    </#list>

    public ${table_cap}Table() {
        super("order");

        <#list ["id", "name", "city_id", "country_id"] as x>
        this.insertFields(${x});
        </#list>

        this.setPkField(id);
    }

}
