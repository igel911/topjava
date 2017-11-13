package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getFilteredWithExceeded(String dateFrom, String dateTo,
                                                        String timeFrom, String timeTo) {
        LocalDate startDate = Objects.isNull(dateFrom) ? LocalDate.MIN : LocalDate.parse(dateFrom);
        LocalDate endDate = Objects.isNull(dateTo) ? LocalDate.MAX : LocalDate.parse(dateTo);
        LocalTime startTime = Objects.isNull(timeFrom) ? LocalTime.MIN : LocalTime.parse(timeFrom);
        LocalTime endTime = Objects.isNull(timeTo) ? LocalTime.MAX : LocalTime.parse(timeTo);
        return getWithExceeded().stream()
                .filter(meal -> DateTimeUtil.isBetweenDates(meal.getDateTime().toLocalDate(), startDate, endDate))
                .filter(meal -> DateTimeUtil.isBetweenTimes(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    public List<MealWithExceed> getWithExceeded() {
        return MealsUtil.getWithExceeded(getAll(), AuthorizedUser.getCaloriesPerDay());
    }

    public Collection<Meal> getAll() {
        int userId = AuthorizedUser.id();
        log.info("get all meals by user with id= {}", userId);
        return service.getAll(userId);
    }

    public Collection<Meal> getFiltered(LocalDate startDate, LocalDate endDate) {
        int userId = AuthorizedUser.id();
        log.info("get filtered by date meals by user with id= {}", userId);
        return service.getFiltered(userId, startDate, endDate);
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
        service.update(userId, meal);
    }
}