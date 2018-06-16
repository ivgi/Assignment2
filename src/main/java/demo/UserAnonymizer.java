package demo;

import org.springframework.stereotype.Component;

@Component
public class UserAnonymizer {

    public User anonymize(User user) {
        user.setName("Anonymous");
        return user;
    }

    public User deAnonymize(User user) {
        user.setName("Know user");
        return user;
    }

}
