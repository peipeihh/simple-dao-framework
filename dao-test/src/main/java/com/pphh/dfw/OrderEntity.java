package com.pphh.dfw;

import com.pphh.dfw.core.IEntity;

import javax.persistence.*;
import java.sql.Date;

/**
 * a table entity definition which is used to map table columns to java entities
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
@Table(name = "order")
public class OrderEntity implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "city_id")
    private Integer cityID;

    @Column(name = "country_id")
    private Integer countryID;

    @Column(name = "update_time")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCityID() {
        return cityID;
    }

    public void setCityID(Integer cityID) {
        this.cityID = cityID;
    }

    public Integer getCountryID() {
        return countryID;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
