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

    public static final FullTypeMysqlTable FULL_TYPE_MYSQL = new FullTypeMysqlTable();

    public final TableField id = new TableField("id");
    public final TableField mediumInt = new TableField("medium_int");
    public final TableField integerVal = new TableField("integer_val");
    public final TableField tinyInt = new TableField("tiny_int");
    public final TableField smallInt = new TableField("small_int");
    public final TableField bigInt = new TableField("big_int");
    public final TableField floatVal = new TableField("float_val");
    public final TableField doubleVal = new TableField("double_val");
    public final TableField numricVal = new TableField("numric_val");
    public final TableField decimalVal = new TableField("decimal_val");
    public final TableField charVal = new TableField("char_val");
    public final TableField varchar45 = new TableField("varchar_45");
    public final TableField tinyBlob = new TableField("tiny_blob");
    public final TableField blobVal = new TableField("blob_val");
    public final TableField longBlob = new TableField("long_blob");
    public final TableField tinyText = new TableField("tiny_text");
    public final TableField textVal = new TableField("text_val");
    public final TableField mediumText = new TableField("medium_text");
    public final TableField longText = new TableField("long_text");
    public final TableField dateVal = new TableField("date_val");
    public final TableField yearVal = new TableField("year_val");
    public final TableField timeVal = new TableField("time_val");
    public final TableField datetimeVal = new TableField("datetime_val");
    public final TableField timestampVal = new TableField("timestamp_val");
    public final TableField updateTime = new TableField("update_time");

    public FullTypeMysqlTable() {
        super("full_type_mysql");
        this.insertFields(id);
        this.insertFields(mediumInt);
        this.insertFields(integerVal);
        this.insertFields(tinyInt);
        this.insertFields(smallInt);
        this.insertFields(bigInt);
        this.insertFields(floatVal);
        this.insertFields(doubleVal);
        this.insertFields(numricVal);
        this.insertFields(decimalVal);
        this.insertFields(charVal);
        this.insertFields(varchar45);
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
        this.insertFields(updateTime);
        this.setPkField(id);
    }

}
