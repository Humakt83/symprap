package fi.ukkosnetti.symprap.util;

import fi.ukkosnetti.symprap.dto.User;
import fi.ukkosnetti.symprap.dto.UserRole;

public class CurrentUser {

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        CurrentUser.currentUser = currentUser;
    }

    public static boolean isTeenUser() {
        return currentUser != null && currentUser.roles.contains(UserRole.TEEN);
    }
}
