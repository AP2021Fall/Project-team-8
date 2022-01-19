package app.jira.controller;

import app.jira.model.dao.BoardDao;
import app.jira.model.domain.Board;
import app.jira.model.domain.Respond;

public class ScoreboardController extends Controller {
    /* Instance Fields */
    public Respond showScoreboard(Board board) throws Exception {
        return new Respond(true, BoardDao.getScoreboardByBoard(board));
    }
}
