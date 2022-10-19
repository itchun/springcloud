package com.itchun.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "apple_info")
public class AppleInfoEntity implements Serializable {

    @Id
    @Column(unique = true, columnDefinition = "char(32)")
    private String id;

    @Column
    private String name;

    @Column
    private String number;

    @Column
    private String savetime;
}
