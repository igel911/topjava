package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.implement.MealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static String ADD_OR_UPDATE = "meal.jsp";

    private static String MEALS_LIST = "meals_list.jsp";

    private static final Logger log = getLogger(MealServlet.class);

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String forward = "";
        MealDao mealDao = new MealDaoInMemory();
        String action = request.getParameter("action");
        int caloriesPerDay = 2000;
        if (action == null || action.isEmpty()) {
            forward = MEALS_LIST;
            request.setAttribute("mealsWithExceed", MealsUtil.getAllWithExceeded(mealDao.getAll(), caloriesPerDay));
            request.setAttribute("formatter", formatter);
        } else {
            if (action.equalsIgnoreCase("delete")){
                mealDao.delete(Integer.parseInt(request.getParameter("mealId")));
                forward = MEALS_LIST;
                request.setAttribute("mealsWithExceed", MealsUtil.getAllWithExceeded(mealDao.getAll(), caloriesPerDay));
                request.setAttribute("formatter", formatter);
            } else if (action.equalsIgnoreCase("update")){
                forward = ADD_OR_UPDATE;
                Meal meal = mealDao.getById(Integer.parseInt(request.getParameter("mealId")));
                request.setAttribute("meal", meal);
            } else if (action.equalsIgnoreCase("add")){
                forward = ADD_OR_UPDATE;
            }
        }
        log.debug("forward to meals from doGet");
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        MealDao mealDao = new MealDaoInMemory();
        String date = request.getParameter("dateTime");
        LocalDateTime dateTime;
        if(date == null || date.isEmpty()) {
            dateTime = LocalDateTime.now();
        } else {
            dateTime = LocalDateTime.parse(date);
        }
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);
        String mealId = request.getParameter("mealId");
        if(mealId == null || mealId.isEmpty()) {
            mealDao.add(meal);
        } else {
            meal.setId(Integer.parseInt(mealId));
            mealDao.update(meal);
        }
        log.debug("redirect to meals from doPost");
        response.sendRedirect("meals");
    }
}
