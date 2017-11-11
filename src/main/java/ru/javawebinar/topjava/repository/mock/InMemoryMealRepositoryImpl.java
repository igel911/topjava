package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, List<Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        new InMemoryUserRepositoryImpl().getAll().forEach(
                user -> new MealsUtil().MEALS.forEach(
                        meal -> save(user.getId(), meal)));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else {
            delete(userId, meal.getId());
        }
        List<Meal> userMeals = repository.getOrDefault(userId, new ArrayList<>());
        userMeals.add(meal);
        repository.putIfAbsent(userId, userMeals);
        return meal;
    }

    @Override
    public void delete(int userId, int mealId) {
        List<Meal> userMeals = repository.get(userId);
        if (Objects.nonNull(userMeals))
            userMeals.remove(get(userId, mealId));
    }

    @Override
    public Meal get(int userId, int mealId) {
        List<Meal> userMeals = repository.get(userId);
        Meal meal = null;
        if (Objects.nonNull(userMeals)) {
            meal = userMeals.stream()
                    .filter(m -> m.getId() == (mealId))
                    .findFirst()
                    .orElse(null);
        }
         return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        List<Meal> userMeals = repository.get(userId);
        if (Objects.nonNull(userMeals)) {
            userMeals.sort(Comparator.comparing(Meal::getDateTime).reversed());
            return userMeals;
        }
        return Collections.EMPTY_LIST;
    }
}

