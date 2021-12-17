package controller;

import model.dao.TaskDao;
import model.domain.Board;
import model.domain.Respond;

public class RoadmapController extends Controller {
    /* Instance Fields */
    public Respond showRoadmap(Board board) throws Exception {
        return new Respond(true, TaskDao.getRoadmapByBoard(board));
    }
}
