package util;

import model.Post;
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

    public static List<Post> samplePosts() {
        List<Post> samplePosts = new ArrayList<>();
        samplePosts.add(new Post("alee3", "2020-11-19 04:02:00",
                "Never Go Inside Cellphones At Night"));
        samplePosts.add(new Post("alee3", "2013-05-01 21:23:00",
                "How Can Mirrors Be Real If Our Eyes Aren't Real"));
        samplePosts.add(new Post("alee3", "2013-03-23 19:14:00",
                "Most Trees Are Blue"));
        samplePosts.add(new Post("mgreen4", "2013-03-24 13:33:00",
                "Sick of having to go to 2 different huts to buy pizza & sunglasses."));
        samplePosts.add(new Post("mgreen4", "2015-03-12 17:59:00",
                "You'll never be as lazy as whoever named the fireplace"));
        samplePosts.add(new Post("mgreen4", "2014-12-28 00:12:00",
                "barn owls must have been stoked when the barn was invented"));
        samplePosts.add(new Post("jclark5", "2021-06-18 11:18:00",
                "\\\"are you ok?\\\" no i got my sleeve wet washing the dishes"));
        samplePosts.add(new Post("jclark5", "2021-08-30 07:59:00",
                "cows are very calm considering the whole floor is food"));
        samplePosts.add(new Post("jclark5", "2019-04-03 14:24:00",
                "Joaquin is just quinoa pronounced in reverse"));
        return samplePosts;
    }
}
