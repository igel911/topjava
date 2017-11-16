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
                        meal -> {
                            meal.setUserId(user.getId());
                            save(user.getId(), meal);
                        }));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if (Objects.isNull(get(userId, meal.getId()))){
            return null;
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        return Objects.nonNull(get(userId, mealId)) && Objects.nonNull(repository.remove(mealId));
    }

    @Override
    public Meal get(int userId, int mealId) {
        Meal meal = repository.get(mealId);
        return (Objects.nonNull(meal) && userId == meal.getUserId()) ? meal : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getFiltered(userId, LocalDate.MIN, LocalDate.MAX);
    }

    @Override
    public Collection<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

