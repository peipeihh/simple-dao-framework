<#function camelToDashed(s)>
    <#return s
    <#-- "fooBar" to "foo_bar": -->
    ?replace('([a-z])([A-Z])', '$1_$2', 'r')
    <#-- "FOOBar" to "FOO_Bar": -->
    ?replace('([A-Z])([A-Z][a-z])', '$1_$2', 'r')
    <#-- All of those to "foo_bar": -->
    ?lower_case
    >
</#function>
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
    private String ${dashedToCamel(x)?uncap_first};
    </#list>

    <#list ["id", "name", "city_id", "country_id"] as x>
    public String get${dashedToCamel(x)}() {
        return ${dashedToCamel(x)?uncap_first};
    }

    public void set${dashedToCamel(x)}(Integer ${dashedToCamel(x)?uncap_first}) {
        this.${dashedToCamel(x)?uncap_first} = ${dashedToCamel(x)?uncap_first};
    }

    </#list>
}
