package fi.ukkosnetti.symprap.dto;

import java.util.ArrayList;
import java.util.List;

public class User {

    public final Long id;

    public final String userName;

    public final List<Disease> diseases;

    public User(Long id, String userName, List<Disease> diseases) {
        this.id = id;
        this.userName = userName;
        this.diseases = diseases;
    }

    public User() {
        this(null, null, new ArrayList<Disease>());
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
