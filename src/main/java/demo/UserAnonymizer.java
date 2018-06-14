package demo;

import org.springframework.stereotype.Component;

@Component
public class UserAnonymizer {

    public User anonymize(User user) {
        System.out.println("Anonimizing user: " + user.toString());
        User anonymousUser = new User();
        anonymousUser.setAge(user.getAge());
        anonymousUser.setId(user.getId());
        user.setName("Anonymous");
        return anonymousUser;
    }

}
