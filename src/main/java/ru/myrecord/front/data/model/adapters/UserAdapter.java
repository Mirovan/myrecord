package ru.myrecord.front.data.model.adapters;

import ru.myrecord.front.data.model.entities.User;

public class UserAdapter {

    private Integer id;
    private String name;
    private String sirname;

    public UserAdapter(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.sirname = user.getSirname();
    }

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

    public String getSirname() {
        return sirname;
    }

    public void setSirname(String sirname) {
        this.sirname = sirname;
    }
}
