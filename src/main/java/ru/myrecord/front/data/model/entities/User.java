package ru.myrecord.front.data.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Transactional
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "pass")
    @Length(min = 6)
    @JsonIgnore
    private String pass;

    //Линковка на самого себя. NULL - если объект у объекта нет родителя
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_user_id")
    private User ownerUser;

    @Column(name = "name")
    private String name;

    @Column(name = "sirname")
    private String sirname;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnore
    private Set<Role> roles;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UserRoom> userRooms;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UserProduct> userProducts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSirname() {
        return sirname;
    }

    public void setSirname(String sirname) {
        this.sirname = sirname;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<UserRoom> getUserRooms() {
        return userRooms;
    }

    public void setUserRooms(Set<UserRoom> userRooms) {
        this.userRooms = userRooms;
    }

    public Set<UserProduct> getUserProducts() {
        return userProducts;
    }

    public void setUserProducts(Set<UserProduct> userProducts) {
        this.userProducts = userProducts;
    }
}