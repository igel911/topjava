package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int USERS_MEAL_ID = 100002;
    public static final int NOT_USERS_MEAL_ID = 100006;
    public static final Meal meal1 = new Meal(USERS_MEAL_ID, LocalDateTime.of(2015, Month.MAY, 1, 10, 0), "завтрак", 500);
    public static final Meal meal2 = new Meal(100003, LocalDateTime.of(2015, Month.MAY, 1, 14, 0), "обед", 1000);
    public static final Meal meal3 = new Meal(100004, LocalDateTime.of(2015, Month.MAY, 1, 20, 0), "ужин", 500);
    public static final Meal meal4 = new Meal(100005, LocalDateTime.of(2015, Month.MAY, 2, 10, 0), "завтрак", 500);
    public static final Meal notUsersMeal = new Meal(NOT_USERS_MEAL_ID, LocalDateTime.of(2015, Month.MAY, 1, 10, 0), "завтрак", 500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        //assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
