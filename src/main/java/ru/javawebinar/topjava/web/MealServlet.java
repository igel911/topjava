package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController controller;

    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String mealId = request.getParameter("id");
        Meal meal = new Meal(mealId.isEmpty() ? null : Integer.valueOf(mealId),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if (meal.isNew()) {
            controller.create(meal);
            log.info("Create {}", meal);
        } else {
            controller.update(meal, Integer.valueOf(mealId));
            log.info("Update {}", meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                int mealId = getMealId(request);
                log.info("Delete {}", mealId);
                controller.delete(mealId);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(getMealId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                String filter = request.getParameter("btn");
                doFilter(request, filter);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getMealId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    private void doFilter(HttpServletRequest request, String filter) {
        HttpSession session = request.getSession();
        String dateFrom = "";
        String dateTo = "";
        String timeFrom = "";
        String timeTo = "";
        if (filter != null) {
            if (filter.equals("filter")) {
                dateFrom = request.getParameter("dateFrom");
                dateTo = request.getParameter("dateTo");
                timeFrom = request.getParameter("timeFrom");
                timeTo = request.getParameter("timeTo");
            }
            session.setAttribute("dateFrom", dateFrom);
            session.setAttribute("dateTo", dateTo);
            session.setAttribute("timeFrom", timeFrom);
            session.setAttribute("timeTo", timeTo);
        }
        request.setAttribute("meals", controller.getFilteredWithExceeded(
                (String) session.getAttribute("dateFrom"),
                (String) session.getAttribute("dateTo"),
                (String) session.getAttribute("timeFrom"),
                (String) session.getAttribute("timeTo")));
    }
}
