package fi.ukkosnetti.symprap.dto;

import java.util.ArrayList;
import java.util.List;

public class User {

    public enum UserRole {
        ADMIN, TEEN, FOLLOWER;
    }

    public final Long id;

    public final String userName;

    public final List<Disease> diseases;

    public final List<UserRole> roles;

    public User(Long id, String userName, List<Disease> diseases, List<UserRole> roles) {
        this.id = id;
        this.userName = userName;
        this.diseases = diseases;
        this.roles = roles;
    }

    public User() {
        this(null, null, new ArrayList<Disease>(), new ArrayList<UserRole>());
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
