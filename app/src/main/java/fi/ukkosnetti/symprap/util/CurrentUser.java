package fi.ukkosnetti.symprap.util;

import fi.ukkosnetti.symprap.dto.User;
import fi.ukkosnetti.symprap.dto.UserRole;

/**
 * Utility class that provides static methods for setting and getting the current user.
 */
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
