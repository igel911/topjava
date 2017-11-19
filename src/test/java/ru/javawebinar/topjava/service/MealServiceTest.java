package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(USERS_MEAL_ID, USER_ID);
        assertMatch(meal, meal1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(NOT_USERS_MEAL_ID, USER_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(USERS_MEAL_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), meal4, meal3, meal2);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(NOT_USERS_MEAL_ID, USER_ID);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> meals = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 1, 10, 0),
                LocalDateTime.of(2015, Month.MAY, 1, 20, 0), USER_ID);
        assertMatch(meals, meal3, meal2, meal1);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, meal4, meal3, meal2, meal1);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(meal1);
        updated.setCalories(1000);
        service.update(updated, USER_ID);
        assertMatch(service.get(meal1.getId(), USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() throws Exception {
        Meal updated = new Meal(notUsersMeal);
        updated.setCalories(1000);
        service.update(updated, USER_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.now(), "ужин", 600);
        Meal createdMeal = service.create(newMeal, USER_ID);
        newMeal.setId(createdMeal.getId());
        assertMatch(service.getAll(USER_ID), newMeal, meal4, meal3, meal2, meal1);
    }

}