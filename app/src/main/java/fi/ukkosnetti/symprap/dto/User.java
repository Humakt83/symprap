package fi.ukkosnetti.symprap.dto;

import java.util.ArrayList;
import java.util.List;

public class User {

    public final Long id;

    public final String userName;

    public final List<Disease> diseases;

    public final List<UserRole> roles;

    public final List<String> followers;

    public User(Long id, String userName, List<Disease> diseases, List<UserRole> roles, List<String> followers) {
        this.id = id;
        this.userName = userName;
        this.diseases = diseases;
        this.roles = roles;
        this.followers = followers;
    }

    public User() {
        this(null, null, new ArrayList<Disease>(), new ArrayList<UserRole>(), new ArrayList<String>());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", diseases=" + diseases +
                '}';
    }
}
