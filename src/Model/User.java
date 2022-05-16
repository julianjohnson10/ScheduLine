package Model;

public class User {
    private static User currentUser;
    /**
     * Forgot to change these to public, in order to be accessed by all objects.
     */
    public int userId;
    public String userName;

    public User(){

    }

    public static void addUser(User newUser){
        currentUser = newUser;
    }

    public static User getUser() {
        return currentUser;
    }

    /**
     *
     * @return the User's id number
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @return the User's username
     */
    public String getUserName() {
        return userName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
