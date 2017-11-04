package ru.javawebinar.topjava.dao.implement;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemory implements MealDao {

    private static AtomicInteger atomInt = new AtomicInteger();

    private static Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
    static {
        mealsMap.put(atomInt.incrementAndGet(), new Meal(atomInt.get(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealsMap.put(atomInt.incrementAndGet(), new Meal(atomInt.get(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealsMap.put(atomInt.incrementAndGet(), new Meal(atomInt.get(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealsMap.put(atomInt.incrementAndGet(), new Meal(atomInt.get(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealsMap.put(atomInt.incrementAndGet(), new Meal(atomInt.get(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealsMap.put(atomInt.incrementAndGet(), new Meal(atomInt.get(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public void add(Meal meal) {
        update(meal);
    }

    public void update(Meal meal) {
        mealsMap.put(meal.getId(), meal);
    }

    public void delete(Meal meal) {
        mealsMap.remove(meal.getId());
    }

    public List<Meal> getAll() {
        return new ArrayList<>(mealsMap.values());
    }

    public Meal getById(int id) {
        return mealsMap.get(id);
    }

    public static Map<Integer, Meal> getMealsMap() {
        return mealsMap;
    }

    public int getNewId() {
        return atomInt.incrementAndGet();
    }
}
