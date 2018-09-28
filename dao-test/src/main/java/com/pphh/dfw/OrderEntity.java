package com.pphh.dfw;

import javax.persistence.*;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 9/27/2018
 */
@Table(name = "order")
public class OrderEntity {

    @Id
    @Column(name = "PeopleID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer peopleID;

    @Column(name = "Name")
    private String name;

    @Column(name = "CityID")
    private Integer cityID;

    @Column(name = "ProvinceID")
    private Integer provinceID;

    @Column(name = "CountryID")
    private Integer countryID;
}
