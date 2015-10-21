package fi.ukkosnetti.symprap;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fi.ukkosnetti.symprap.dto.UserCreate;
import fi.ukkosnetti.symprap.dto.UserRole;
import fi.ukkosnetti.symprap.proxy.SymprapConnector;
import fi.ukkosnetti.symprap.util.Constants;
import retrofit.client.Response;

public class RegisterActivity extends Activity {

    protected @Bind(R.id.usernameRegistration) EditText usernameField;

    protected @Bind(R.id.passwordRegistration) EditText passwordField;

    protected @Bind(R.id.confirmPasswordRegistration) EditText confirmPasswordField;

    protected @Bind(R.id.firstName) EditText firstNameField;

    protected @Bind(R.id.lastName) EditText lastNameField;

    protected @Bind(R.id.dateOfBirth) EditText dateOfBirthField;

    protected @Bind(R.id.medicalRecordNumber) EditText medicalRecordNumberField;

    protected @Bind(R.id.registrationForm) View registrationForm;

    protected @Bind(R.id.registrationProgress) View progressView;

    private UserRole userType = UserRole.TEEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.radioTeen)
    public void selectTeenUserType() {
        medicalRecordNumberField.setEnabled(true);
        userType = UserRole.TEEN;
    }

    @OnClick(R.id.radioFollower)
    public void selectFollowerUserType() {
        medicalRecordNumberField.setEnabled(false);
        userType = UserRole.FOLLOWER;
    }

    @OnClick(R.id.register)
    public void register() {
        if (areFieldsValid()) {
            try {
                showProgress(true);
                new RegistrationTask().execute(new UserCreate(
                        usernameField.getText().toString().trim(),
                        passwordField.getText().toString().trim(),
                        firstNameField.getText().toString().trim(),
                        lastNameField.getText().toString().trim(),
                        Constants.DATE_FORMATTER.parse(dateOfBirthField.getText().toString()),
                        userType == UserRole.TEEN ? Long.parseLong(medicalRecordNumberField.getText().toString()) : null,
                        Arrays.asList(userType),
                        null
                ));
            } catch (ParseException e) {
                Toast.makeText(getApplicationContext(), "Could not parse date", Toast.LENGTH_SHORT);
                showProgress(false);
            }
        }
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
        return userType == UserRole.TEEN ? isEmpty(medicalRecordNumberField) : false;
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
        if (passwordField.getText().toString().length() < Constants.PASSWORD_MINIMUM_LENGTH) {
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            registrationForm.setVisibility(show ? View.GONE : View.VISIBLE);
            registrationForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    registrationForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            registrationForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private class RegistrationTask extends AsyncTask<UserCreate, Void, Boolean> {

        @Override
        protected Boolean doInBackground(UserCreate... params) {
            try {
                return SymprapConnector.publicMethods().registerUser(params[0]) != null;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            showProgress(false);
            if (success) {
                startActivity(new Intent(
                        RegisterActivity.this,
                        LoginActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT);
            }
        }
    }

}
