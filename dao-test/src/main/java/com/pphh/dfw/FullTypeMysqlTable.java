package com.pphh.dfw;

import com.pphh.dfw.table.AbstractTable;
import com.pphh.dfw.table.TableField;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 2019/3/20
 */
public class FullTypeMysqlTable extends AbstractTable {

    public static final OrderTable ORDER = new OrderTable();

    public final TableField id = new TableField("id");
    public final TableField integer = new TableField("integer");
    public final TableField tinyInt = new TableField("tiny_int");
    public final TableField smallInt = new TableField("small_int");
    public final TableField bigInt = new TableField("big_int");
    public final TableField floatVal = new TableField("float");
    public final TableField doubleVal = new TableField("double");
    public final TableField numricVal = new TableField("numric");
    public final TableField decimalVal = new TableField("decimal");
    public final TableField charVal = new TableField("char");
    public final TableField varchar45Val = new TableField("varchar_45");
    public final TableField tinyBlob = new TableField("tiny_blob");
    public final TableField blobVal = new TableField("blob");
    public final TableField longBlob = new TableField("long_blob");
    public final TableField tinyText = new TableField("tiny_text");
    public final TableField textVal = new TableField("text");
    public final TableField mediumText = new TableField("medium_text");
    public final TableField longText = new TableField("long_text");
    public final TableField dateVal = new TableField("date");
    public final TableField yearVal = new TableField("year");
    public final TableField timeVal = new TableField("time");
    public final TableField datetimeVal = new TableField("datetime");
    public final TableField timestampVal = new TableField("timestamp");
    public final TableField updateVal = new TableField("update");

    public FullTypeMysqlTable() {
        super("full_type_mysql");
        this.insertFields(id);
        this.insertFields(integer);
        this.insertFields(tinyInt);
        this.insertFields(smallInt);
        this.insertFields(bigInt);
        this.insertFields(floatVal);
        this.insertFields(doubleVal);
        this.insertFields(numricVal);
        this.insertFields(decimalVal);
        this.insertFields(charVal);
        this.insertFields(varchar45Val);
        this.insertFields(tinyBlob);
        this.insertFields(blobVal);
        this.insertFields(longBlob);
        this.insertFields(tinyText);
        this.insertFields(textVal);
        this.insertFields(mediumText);
        this.insertFields(longText);
        this.insertFields(dateVal);
        this.insertFields(yearVal);
        this.insertFields(timeVal);
        this.insertFields(datetimeVal);
        this.insertFields(timestampVal);
        this.insertFields(updateVal);
        this.setPkField(id);
    }

}
