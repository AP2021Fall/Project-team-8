package app.jira.controller;

import app.jira.model.dao.TaskDao;
import app.jira.model.domain.Respond;
import app.jira.model.domain.User;


public class CalendarController extends Controller {
    /* Instance Methods */
    public Respond showCalendar(User loggedInUser) throws Exception {
        return new Respond(true, TaskDao.getTaskByUser(loggedInUser));
    }
}
