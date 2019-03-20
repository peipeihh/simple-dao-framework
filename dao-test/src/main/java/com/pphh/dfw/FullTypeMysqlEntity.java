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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "medium_int")
    private Integer mediumIntVal;

    @Column(name = "integer")
    private Integer integerVal;

    @Column(name = "tiny_int")
    private Byte tinyIntVal;

    @Column(name = "small_int")
    private Short smallIntVal;

    @Column(name = "big_int")
    private Long bigIntVal;

    @Column(name = "float")
    private Double floatVal;

    @Column(name = "double")
    private Double doubleVal;

    @Column(name = "numric")
    private BigDecimal numricVal;

    @Column(name = "decimal")
    private BigDecimal decimalVal;

    @Column(name = "char")
    private String charVal;

    @Column(name = "varchar_45")
    private String varchar45Val;

    @Column(name = "tiny_blob")
    private Blob tinyBlobVal;

    @Column(name = "blob")
    private Blob blobVal;

    @Column(name = "long_blob")
    private Blob longBlobVal;

    @Column(name = "tiny_text")
    private String tinyTextVal;

    @Column(name = "text")
    private String textVal;

    @Column(name = "medium_text")
    private String mediumTextVal;

    @Column(name = "long_text")
    private String longTextVal;

    @Column(name = "date")
    private Date dateVal;

    @Column(name = "year")
    private Date yearVal;

    @Column(name = "time")
    private Time timeVal;

    @Column(name = "datetime")
    private Timestamp datetimeVal;

    @Column(name = "timestamp")
    private Timestamp timestampVal;

    @Column(name = "update")
    private Timestamp updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMediumIntVal() {
        return mediumIntVal;
    }

    public void setMediumIntVal(Integer mediumIntVal) {
        this.mediumIntVal = mediumIntVal;
    }

    public Integer getIntegerVal() {
        return integerVal;
    }

    public void setIntegerVal(Integer integerVal) {
        this.integerVal = integerVal;
    }

    public Byte getTinyIntVal() {
        return tinyIntVal;
    }

    public void setTinyIntVal(Byte tinyIntVal) {
        this.tinyIntVal = tinyIntVal;
    }

    public Short getSmallIntVal() {
        return smallIntVal;
    }

    public void setSmallIntVal(Short smallIntVal) {
        this.smallIntVal = smallIntVal;
    }

    public Long getBigIntVal() {
        return bigIntVal;
    }

    public void setBigIntVal(Long bigIntVal) {
        this.bigIntVal = bigIntVal;
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

    public String getVarchar45Val() {
        return varchar45Val;
    }

    public void setVarchar45Val(String varchar45Val) {
        this.varchar45Val = varchar45Val;
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

    public String getMediumTextVal() {
        return mediumTextVal;
    }

    public void setMediumTextVal(String mediumTextVal) {
        this.mediumTextVal = mediumTextVal;
    }

    public String getLongTextVal() {
        return longTextVal;
    }

    public void setLongTextVal(String longTextVal) {
        this.longTextVal = longTextVal;
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
