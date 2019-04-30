package com.pphh.dfw;

import com.pphh.dfw.core.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 2019/3/20
 */
@Table(name = "full_type_mysql")
public class FullTypeMysqlEntity implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "medium_int")
    private Integer mediumInt;

    @Column(name = "integer_val")
    private Integer integerVal;

    @Column(name = "tiny_int")
    private Byte tinyInt;

    @Column(name = "small_int")
    private Short smallInt;

    @Column(name = "big_int")
    private Long bigInt;

    @Column(name = "float_val")
    private Double floatVal;

    @Column(name = "double_val")
    private Double doubleVal;

    @Column(name = "numric_val")
    private BigDecimal numricVal;

    @Column(name = "decimal_val")
    private BigDecimal decimalVal;

    @Column(name = "char_val")
    private String charVal;

    @Column(name = "varchar_45")
    private String varchar45;

    @Column(name = "tiny_blob")
    private Blob tinyBlobVal;

    @Column(name = "blob_val")
    private Blob blobVal;

    @Column(name = "long_blob")
    private Blob longBlobVal;

    @Column(name = "tiny_text")
    private String tinyTextVal;

    @Column(name = "text_val")
    private String textVal;

    @Column(name = "medium_text")
    private String mediumText;

    @Column(name = "long_text")
    private String longText;

    @Column(name = "date_val")
    private Date dateVal;

    @Column(name = "year_val")
    private Date yearVal;

    @Column(name = "time_val")
    private Time timeVal;

    @Column(name = "datetime_val")
    private Timestamp datetimeVal;

    @Column(name = "timestamp_val")
    private Timestamp timestampVal;

    @Column(name = "update_time")
    private Timestamp updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMediumInt() {
        return mediumInt;
    }

    public void setMediumInt(Integer mediumInt) {
        this.mediumInt = mediumInt;
    }

    public Integer getIntegerVal() {
        return integerVal;
    }

    public void setIntegerVal(Integer integerVal) {
        this.integerVal = integerVal;
    }

    public Byte getTinyInt() {
        return tinyInt;
    }

    public void setTinyInt(Byte tinyInt) {
        this.tinyInt = tinyInt;
    }

    public Short getSmallInt() {
        return smallInt;
    }

    public void setSmallInt(Short smallInt) {
        this.smallInt = smallInt;
    }

    public Long getBigInt() {
        return bigInt;
    }

    public void setBigInt(Long bigInt) {
        this.bigInt = bigInt;
    }

    public Double getFloatVal() {
        return floatVal;
    }

    public void setFloatVal(Double floatVal) {
        this.floatVal = floatVal;
    }

    public Double getDoubleVal() {
        return doubleVal;
    }

    public void setDoubleVal(Double doubleVal) {
        this.doubleVal = doubleVal;
    }

    public BigDecimal getNumricVal() {
        return numricVal;
    }

    public void setNumricVal(BigDecimal numricVal) {
        this.numricVal = numricVal;
    }

    public BigDecimal getDecimalVal() {
        return decimalVal;
    }

    public void setDecimalVal(BigDecimal decimalVal) {
        this.decimalVal = decimalVal;
    }

    public String getCharVal() {
        return charVal;
    }

    public void setCharVal(String charVal) {
        this.charVal = charVal;
    }

    public String getVarchar45() {
        return varchar45;
    }

    public void setVarchar45(String varchar45) {
        this.varchar45 = varchar45;
    }

    public Blob getTinyBlobVal() {
        return tinyBlobVal;
    }

    public void setTinyBlobVal(Blob tinyBlobVal) {
        this.tinyBlobVal = tinyBlobVal;
    }

    public Blob getBlobVal() {
        return blobVal;
    }

    public void setBlobVal(Blob blobVal) {
        this.blobVal = blobVal;
    }

    public Blob getLongBlobVal() {
        return longBlobVal;
    }

    public void setLongBlobVal(Blob longBlobVal) {
        this.longBlobVal = longBlobVal;
    }

    public String getTinyTextVal() {
        return tinyTextVal;
    }

    public void setTinyTextVal(String tinyTextVal) {
        this.tinyTextVal = tinyTextVal;
    }

    public String getTextVal() {
        return textVal;
    }

    public void setTextVal(String textVal) {
        this.textVal = textVal;
    }

    public String getMediumText() {
        return mediumText;
    }

    public void setMediumText(String mediumText) {
        this.mediumText = mediumText;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public Date getDateVal() {
        return dateVal;
    }

    public void setDateVal(Date dateVal) {
        this.dateVal = dateVal;
    }

    public Date getYearVal() {
        return yearVal;
    }

    public void setYearVal(Date yearVal) {
        this.yearVal = yearVal;
    }

    public Time getTimeVal() {
        return timeVal;
    }

    public void setTimeVal(Time timeVal) {
        this.timeVal = timeVal;
    }

    public Timestamp getDatetimeVal() {
        return datetimeVal;
    }

    public void setDatetimeVal(Timestamp datetimeVal) {
        this.datetimeVal = datetimeVal;
    }

    public Timestamp getTimestampVal() {
        return timestampVal;
    }

    public void setTimestampVal(Timestamp timestampVal) {
        this.timestampVal = timestampVal;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
