package ru.myrecord.front.data.model.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role")
public class Role {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(id, role1.id) &&
                Objects.equals(role, role1.role) &&
                Objects.equals(roleAbout, role1.roleAbout);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, role, roleAbout);
    }
}
