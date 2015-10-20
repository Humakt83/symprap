package fi.ukkosnetti.symprap;

import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fi.ukkosnetti.symprap.dto.User;
import fi.ukkosnetti.symprap.util.Constants;

public class RegisterActivity extends Activity {

    protected @Bind(R.id.usernameRegistration) EditText usernameField;

    protected @Bind(R.id.passwordRegistration) EditText passwordField;

    protected @Bind(R.id.confirmPasswordRegistration) EditText confirmPasswordField;

    protected @Bind(R.id.firstName) EditText firstNameField;

    protected @Bind(R.id.lastName) EditText lastNameField;

    protected @Bind(R.id.dateOfBirth) EditText dateOfBirthField;

    protected @Bind(R.id.medicalRecordNumber) EditText medicalRecordNumberField;

    private User.UserRole userType = User.UserRole.TEEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.radioTeen)
    public void selectTeenUserType() {
        medicalRecordNumberField.setEnabled(true);
        userType = User.UserRole.TEEN;
    }

    @OnClick(R.id.radioFollower)
    public void selectFollowerUserType() {
        medicalRecordNumberField.setEnabled(false);
        userType = User.UserRole.FOLLOWER;
    }

    @OnClick(R.id.register)
    public void register() {
        boolean valid = areFieldsValid();
    }

    private boolean areFieldsValid() {
        StringBuilder sb = new StringBuilder();
        sb.append(validatePassword());
        sb.append(isEmpty(usernameField) ? "Username is missing\n" : "");
        sb.append(isEmpty(firstNameField) ? "First name is missing\n" : "");
        sb.append(isEmpty(lastNameField) ? "Last name is missing\n" : "");
        sb.append(validateDateOfBirth());
        sb.append(isMedicalRecordMissing() ? "Medical record number is missing\n" : "");
        if (sb.length() > 0) {
            Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean isMedicalRecordMissing() {
        return userType == User.UserRole.TEEN ? isEmpty(medicalRecordNumberField) : false;
    }

    private String validateDateOfBirth() {
        if (isEmpty(dateOfBirthField)) {
            return "Date of birth is missing\n";
        }
        return isDateOfBirthValidDate() ? "" : "Invalid date format. Required: "
                + Constants.DATE_FORMAT
                + "\n";
    }

    private boolean isDateOfBirthValidDate() {
        try {
            String dob = dateOfBirthField.getText().toString().trim();
            Constants.DATE_FORMATTER.parse(dob);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private String validatePassword() {
        String pass = passwordField.getText().toString().trim();
        String confirm = confirmPasswordField.getText().toString().trim();
        if (passwordField.getText().toString().length() < 4) {
            return "Minimum length for password is " + Constants.PASSWORD_MINIMUM_LENGTH + "\n";
        }
        if (!pass.equals(confirm)) {
            return "Passwords does not match.\n";
        }
        return "";
    }

    private boolean isEmpty(EditText field) {
        return field.getText().toString().trim().isEmpty();
    }

}
