package model;

public class User {
    private final String username;
    private final String fullName;

    public User(String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return username + ": " + fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User otherUser = (User) o;
        return username.equals(otherUser.username)
                && fullName.equals(otherUser.fullName);
    }
}
