package util;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class SampleData {
    public static List<User> sampleUsers() {
        List<User> sampleUsers = new ArrayList<>();
        sampleUsers.add(new User("jsmith30","Joe Smith"));
        sampleUsers.add(new User("mjohnson25", "Mary Johnson"));
        return sampleUsers;
    }
}
