package Model;

/**
 * User class.
 */
public class User {
    /**
     * Saves a user to currentUser for data retrieval.
     */
    private static User currentUser;

    /**
     * User ID.
     */
    public int userId;

    /**
     * User name.
     */
    public String userName;

    /**
     * Assigns a specified user to the currentUser variable.
     * @param newUser the user to assign.
     */
    public static void addUser(User newUser){
        currentUser = newUser;
    }

    /**
     * Gets the current User.
     * @return the currentUser.
     */
    public static User getUser() {
        return currentUser;
    }

    /**
     *
     * @return the User's username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user ID.
     * @param userId the user ID to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Sets the user's name.
     * @param userName the username to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
