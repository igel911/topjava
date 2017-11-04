package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

public interface MealDao extends ItemDao<Meal>{

    int getNewId();
}
