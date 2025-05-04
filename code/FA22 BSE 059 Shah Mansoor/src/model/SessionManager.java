package model;

public class SessionManager {
    private static User currentUser;

    public static void startSession(User user) {
        currentUser = user;
    }

    public static void endSession() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
