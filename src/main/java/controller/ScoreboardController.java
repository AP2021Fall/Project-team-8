package controller;

import model.dao.BoardDao;
import model.domain.Board;
import model.domain.Respond;

public class ScoreboardController extends Controller {
    /* Instance Fields */
    public Respond showScoreboard(Board board) throws Exception {
        return new Respond(true, BoardDao.getScoreboardByBoard(board));
    }
}
