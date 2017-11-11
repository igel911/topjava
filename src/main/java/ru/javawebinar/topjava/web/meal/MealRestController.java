package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Collection<Meal> getAll(int userId) {
        log.info("getAll meals by user with id= {}", userId);
        return service.getAll(userId);
    }

    public Meal get(int userId, int mealId) {
        log.info("get meal with id= {} by user with id= {}", mealId, userId);
        return service.get(userId, mealId);
    }

    public Meal create(int userId, Meal meal) {
        log.info("create meal with id= {} by user with id= {}", meal, userId);
        checkNew(meal);
        return service.create(userId, meal);
    }

    public void delete(int userId, int mealId) {
        log.info("delete meal with id= {} by user with id= {}", mealId, userId);
        service.delete(userId, mealId);
    }

    public void update(int userId, Meal meal, int mealId) {
        log.info("update meal= {} with id= {} by user with id= {}", meal, mealId, userId);
        assureIdConsistent(meal, mealId);
        service.update(userId, meal);
    }
}