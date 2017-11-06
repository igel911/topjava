package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.ItemDao;
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

    private static final String ADD_OR_UPDATE = "meal.jsp";

    private static final String MEALS_LIST = "meals_list.jsp";

    private static final String REDIRECT = "meals";

    private static final Logger log = getLogger(MealServlet.class);

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    private ItemDao<Meal> mealDao;

    @Override
    public void init() throws ServletException {
        mealDao = new MealDaoInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            request.setAttribute("mealsWithExceed", MealsUtil.getAllWithExceeded(mealDao.getAll(), 2000));
            request.setAttribute("formatter", formatter);
            log.debug("forward to meals_list.jsp from doGet");
            request.getRequestDispatcher(MEALS_LIST).forward(request, response);
        } else {
            switch (action.toLowerCase()) {
                case "delete": mealDao.delete(Integer.parseInt(request.getParameter("mealId")));
                                log.debug("redirect to \"/meals\" from doGet");
                                response.sendRedirect(REDIRECT); break;
                case "update": Meal meal = mealDao.getById(Integer.parseInt(request.getParameter("mealId")));
                                request.setAttribute("meal", meal);
                case "add":    log.debug("forward to meal.jsp from doGet");
                                request.getRequestDispatcher(ADD_OR_UPDATE).forward(request, response); break;
                default: break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
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
        log.debug("redirect to \"/meals\" from doPost");
        response.sendRedirect(REDIRECT);
    }
}
