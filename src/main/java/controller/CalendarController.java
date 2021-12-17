package controller;

import model.dao.TaskDao;
import model.domain.Respond;
import model.domain.User;


public class CalendarController extends Controller {
    /* Instance Methods */
    public Respond showCalendar(User loggedInUser) throws Exception {
        return new Respond(true, TaskDao.getTaskByUser(loggedInUser));
    }
}
