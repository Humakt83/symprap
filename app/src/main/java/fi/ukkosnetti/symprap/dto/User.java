package fi.ukkosnetti.symprap.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

    public final Long id;

    public final String userName;

    public final List<Symptom> symptoms;

    public User(Long id, String userName, List<Symptom> symptoms) {
        this.id = id;
        this.userName = userName;
        this.symptoms = symptoms;
    }

    public User() {
        this(null, null, new ArrayList<Symptom>());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", symptoms=" + symptoms +
                '}';
    }
}
