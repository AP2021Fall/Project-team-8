package app.jira.controller;

import app.jira.model.dao.TaskDao;
import app.jira.model.domain.Board;
import app.jira.model.domain.Respond;

public class RoadmapController extends Controller {
    /* Instance Fields */
    public Respond showRoadmap(Board board) throws Exception {
        return new Respond(true, TaskDao.getRoadmapByBoard(board));
    }
}
