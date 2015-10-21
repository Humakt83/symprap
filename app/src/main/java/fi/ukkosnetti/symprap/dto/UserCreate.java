package fi.ukkosnetti.symprap.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserCreate {

    public final String userName;

    public final String password;

    public final String firstName;

    public final String lastName;

    public final Long dateOfBirth;

    public final Long medicalRecordNumber;

    public final List<UserRole> roles;

    public final List<Disease> diseases;

    public UserCreate(String userName, String password, String firstName, String lastName, Date dateOfBirth, Long medicalRecordNumber, List<UserRole> roles, List<Disease> diseases) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth.getTime();
        this.medicalRecordNumber = medicalRecordNumber;
        this.roles = roles;
        this.diseases = diseases;
    }

    public UserCreate() {
        this(null, null, null, null, null, null, new ArrayList<UserRole>(), new ArrayList<Disease>());
    }
}
