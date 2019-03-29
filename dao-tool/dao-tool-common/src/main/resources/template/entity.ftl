<#assign table_cap = table?cap_first>
<#assign table_upper = table?upper_case>
package com.pphh.dfw;

import com.pphh.dfw.core.IEntity;

import javax.persistence.*;
import java.sql.Date;

/**
* Please add description here.
*
* @author ${author}
* @date ${date}
*/
@Table(name = "${table}")
public class ${table_cap}Entity implements IEntity {

    <#list ["id", "name", "city_id", "country_id"] as x>
    @Column(name = "${x}")
    private String ${x};
    </#list>

    <#list ["id", "name", "city_id", "country_id"] as x>
    public String get${x}() {
        return ${x};
    }

    public void set${x}(Integer ${x}) {
        this.${x} = ${x};
    }

    </#list>
}
