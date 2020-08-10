package net.thumbtack.school.hospital.model;

import java.util.Objects;

public class Admin extends User {
    private String position;

    public Admin(String firstName, String lastName, String patronymic, String position, String login, String password) {
        this(0, firstName, lastName, patronymic, position, login, password);
    }

    public Admin(Integer id, String firstName, String lastName, String patronymic, String position, String login, String password) {
        super(id, firstName, lastName, patronymic, login, password, "ADMIN");
        this.position = position;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin)) return false;
        if (!super.equals(o)) return false;
        Admin admin = (Admin) o;
        return Objects.equals(getPosition(), admin.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPosition());
    }
}
