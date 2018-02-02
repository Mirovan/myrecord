package ru.myrecord.front.data.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "role")
    private String role;

    @Column(name = "role_about")
    private String roleAbout;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleAbout() {
        return roleAbout;
    }

    public void setRoleAbout(String roleAbout) {
        this.roleAbout = roleAbout;
    }
}
