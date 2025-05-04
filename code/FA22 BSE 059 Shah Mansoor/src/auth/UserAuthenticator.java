package auth;

import dao.UserDAO;
import model.User;

public class UserAuthenticator {
    UserDAO userDAO = new UserDAO();

    public User authenticate(String email, String password) {
        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
