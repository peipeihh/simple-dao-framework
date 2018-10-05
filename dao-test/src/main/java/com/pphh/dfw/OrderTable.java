package com.pphh.dfw;

import com.pphh.dfw.table.AbstractTable;
import com.pphh.dfw.table.TableField;


/**
 * a table definition which is useful to build sql with SqlBuilder
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
public class OrderTable extends AbstractTable {

    public static final OrderTable ORDER = new OrderTable();

    public final TableField id = new TableField("id");
    public final TableField name = new TableField("name");
    public final TableField city_id = new TableField("city_id");
    public final TableField country_id = new TableField("country_id");

    public OrderTable() {
        super("order");
        this.insertFields(id);
        this.insertFields(name);
        this.insertFields(city_id);
        this.insertFields(country_id);
        this.setPkField(id);
    }

}
