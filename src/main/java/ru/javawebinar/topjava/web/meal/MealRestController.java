package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getFilteredWithExceeded(String dateFrom, String dateTo, String timeFrom, String timeTo) {
        log.info("get filtered meals. Dates from {} to {}. Times from {} to {}", dateFrom, dateTo, timeFrom, timeTo);
        return MealsUtil.getFilteredWithExceeded(service.getFiltered(
                AuthorizedUser.id(), getDate(dateFrom, false), getDate(dateTo, true)),
                getTime(timeFrom, false), getTime(timeTo, true), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getWithExceeded() {
        log.info("get all meals");
        return getFilteredWithExceeded(null, null, null, null);
    }

    public Meal get(int mealId) {
        int userId = AuthorizedUser.id();
        log.info("get meal with id= {} by user with id= {}", mealId, userId);
        return service.get(userId, mealId);
    }

    public Meal create(Meal meal) {
        int userId = AuthorizedUser.id();
        log.info("create meal with id= {} by user with id= {}", meal, userId);
        checkNew(meal);
        meal.setUserId(userId);
        return service.create(userId, meal);
    }

    public void delete(int mealId) {
        int userId = AuthorizedUser.id();
        log.info("delete meal with id= {} by user with id= {}", mealId, userId);
        service.delete(userId, mealId);
    }

    public void update(Meal meal, int mealId) {
        int userId = AuthorizedUser.id();
        log.info("update meal= {} with id= {} by user with id= {}", meal, mealId, userId);
        assureIdConsistent(meal, mealId);
        meal.setUserId(userId);
        service.update(userId, meal);
    }

    private LocalTime getTime(String time, boolean max) {
        return (Objects.isNull(time) || time.isEmpty()) ? (max ? LocalTime.MAX : LocalTime.MIN) : LocalTime.parse(time);
    }

    private LocalDate getDate(String date, boolean max) {
        return (Objects.isNull(date) || date.isEmpty()) ? (max ? LocalDate.MAX : LocalDate.MIN) : LocalDate.parse(date);
    }
}