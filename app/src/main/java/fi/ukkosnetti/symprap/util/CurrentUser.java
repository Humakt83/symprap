package fi.ukkosnetti.symprap.util;

import fi.ukkosnetti.symprap.dto.User;

public class CurrentUser {

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        CurrentUser.currentUser = currentUser;
    }
}
