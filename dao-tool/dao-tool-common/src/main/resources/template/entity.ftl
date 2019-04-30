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
* a table entity definition which is used to map table columns to java entities
*
* @author ${author}
* @date ${date}
*/
@Table(name = "${table}")
public class ${table_cap}Entity implements IEntity {

    <#list table_fields as x>
    <#if x == table_fields_pk>
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    </#if>
    @Column(name = "${x}")
    private ${table_field_type_map[x]} ${dashedToCamel(x)?uncap_first};

    </#list>

    <#list table_fields as x>
    public ${table_field_type_map[x]} get${dashedToCamel(x)}() {
        return ${dashedToCamel(x)?uncap_first};
    }

    public void set${dashedToCamel(x)}(${table_field_type_map[x]} ${dashedToCamel(x)?uncap_first}) {
        this.${dashedToCamel(x)?uncap_first} = ${dashedToCamel(x)?uncap_first};
    }

    </#list>
}
