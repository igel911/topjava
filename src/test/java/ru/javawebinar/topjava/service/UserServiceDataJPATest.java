package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class UserServiceDataJPATest extends UserServiceTest {

    @Test
    public void getWithMeals() {
        User user = service.getWithMeals(USER_ID);
        assertMatch(user.getMeals(), MEALS);
    }

    @Test
    public void getWithNoMeals() {
        User user = service.create(new User(null, "userName", "email@com", "password", Role.ROLE_ADMIN));
        user = service.getWithMeals(user.getId());
        assertMatch(user.getMeals(), Collections.emptyList());
    }
}
