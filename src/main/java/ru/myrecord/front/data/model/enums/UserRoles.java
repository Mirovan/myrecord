package ru.myrecord.front.data.model.enums;

import ru.myrecord.front.data.model.entities.Role;

public enum UserRoles {

    ADMIN(1, "ADMIN", "Системный администратор"),
    SYSUSER(2, "SYSUSER", "Системный пользователь"),
    MANAGER(3, "MANAGER", "Менеджер"),
    MASTER(4, "MASTER", "Мастер"),
    CLIENT(5,"CLIENT", "Клиент");

    private int id;
    private String role;
    private String role_about;

    UserRoles(int id, String role, String role_about) {
        this.id = id;
        this.role = role;
        this.role_about = role_about;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getRole_about() {
        return role_about;
    }

    public Role createRole() {
        Role role = new Role();
        role.setRoleAbout(getRole_about());
        role.setRole(getRole());
        role.setId(getId());
        return role;
    }
}
