package ru.javawebinar.topjava.dao.implement;

import ru.javawebinar.topjava.dao.ItemDao;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemory implements ItemDao<Meal> {

    private static AtomicInteger atomInt = new AtomicInteger();

    private static Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
    static {
        Meal meal = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        meal.setId(atomInt.incrementAndGet());
        mealsMap.put(atomInt.get(), meal);
        meal = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
        meal.setId(atomInt.incrementAndGet());
        mealsMap.put(atomInt.get(), meal);
        meal = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
        meal.setId(atomInt.incrementAndGet());
        mealsMap.put(atomInt.get(), meal);
        meal = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
        meal.setId(atomInt.incrementAndGet());
        mealsMap.put(atomInt.get(), meal);
        meal = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
        meal.setId(atomInt.incrementAndGet());
        mealsMap.put(atomInt.get(), meal);
        meal = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
        meal.setId(atomInt.incrementAndGet());
        mealsMap.put(atomInt.get(), meal);
    }

    public void add(Meal meal) {
        meal.setId(atomInt.incrementAndGet());
        update(meal);
    }

    public void update(Meal meal) {
        mealsMap.put(meal.getId(), meal);
    }

    public void delete(int id) {
        mealsMap.remove(id);
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

}
