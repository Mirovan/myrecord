package ru.myrecord.front.data.model;

import org.hibernate.validator.constraints.Length;
import javax.persistence.*;

@Entity
@Table(name = "sys_user")
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "pass")
    @Length(min = 6)
    @Transient
    private String pass;
}