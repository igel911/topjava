package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
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
            meal.setUserId(userId);
        }
        if (meal.getUserId() != userId){
            return null;
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        Meal meal = get(userId, mealId);
        if (Objects.nonNull(meal)) {
           repository.remove(mealId);
           return true;
        }
        return false;
    }

    @Override
    public Meal get(int userId, int mealId) {
        Meal meal = repository.get(mealId);
        if (Objects.nonNull(meal) && userId == meal.getUserId()) {
            return meal;
        }
         return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getFiltered(userId, LocalDate.MIN, LocalDate.MAX);
    }

    @Override
    public Collection<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(meal -> DateTimeUtil.isBetweenDates(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

