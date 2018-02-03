package ru.myrecord.front.data.model.adapters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;

public abstract class IUser {
    @Column(name = "pass")
    @Length(min = 6)
    @JsonIgnore
    private String pass;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }



}
