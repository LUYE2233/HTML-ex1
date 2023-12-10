package org.kukuking.entity;

public class User {
    private String userName;
    private String password;
    private String userID;
    private int groupID;

    public User(String userName, String password, String userID, int groupID) {
        this.userName = userName;
        this.password = password;
        this.userID = userID;
        this.groupID = groupID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public static boolean isCorrect(User user){
        return user.groupID >= 0 && user.groupID <= 2;
    }
}
