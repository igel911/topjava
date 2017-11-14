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
        LocalDate startDate = (Objects.isNull(dateFrom) || dateFrom.isEmpty()) ? LocalDate.MIN : LocalDate.parse(dateFrom);
        LocalDate endDate = (Objects.isNull(dateTo) || dateTo.isEmpty()) ? LocalDate.MAX : LocalDate.parse(dateTo);
        LocalTime startTime = (Objects.isNull(timeFrom) || timeFrom.isEmpty()) ? LocalTime.MIN : LocalTime.parse(timeFrom);
        LocalTime endTime = (Objects.isNull(timeTo) || timeTo.isEmpty()) ? LocalTime.MAX : LocalTime.parse(timeTo);
        return MealsUtil.getFilteredWithExceeded(service.getFiltered(AuthorizedUser.id(), startDate, endDate),
                startTime, endTime, AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getWithExceeded() {
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
}