package util;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class SampleData {
    public static List<User> sampleUsers() {
        List<User> sampleUsers = new ArrayList<>();
        sampleUsers.add(new User("alee3","Alex Lee"));
        sampleUsers.add(new User("mgreen4", "Mary Green"));
        sampleUsers.add(new User("jclark5", "Jane Clark"));
        return sampleUsers;
    }
}
