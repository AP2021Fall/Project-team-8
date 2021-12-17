package controller;

import model.dao.UserDao;
import model.domain.Board;
import model.domain.Date;
import model.domain.Respond;
import model.domain.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.Callable;

public abstract class Controller {
    /* Static Methods */
    public User assertUserExists(String username) throws Exception {
        User user = UserDao.getByUsername(username);
        if (user == null) throw new Exception(String.format("there is no user with username (%s)", username));
        return user;
    }

    public static boolean isInt(String numOrNot) {
        try {
            Integer.parseInt(numOrNot);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected static void authentication(User loggedInUser, Board board) throws Exception {
        if (loggedInUser.getId() != board.getLeader().getId() &&
                !loggedInUser.getUsername().equals(board.getLeader().getUsername()))
            throw new Exception("you do not have the permission to do this action!");

    }

    public static int castToInt(String num, String parameterName) throws Exception {
        try {
            return Integer.parseInt(num);
        } catch (Exception e) {
            throw new Exception(String.format("(%s) should be a number not (%s)", parameterName, num));
        }
    }

    public static Timestamp assertValidTimestamp(String timeStr) throws Exception {
        Timestamp timestamp;
        try {
            timestamp = Timestamp.from(Date.timeFormat.parse(timeStr).toInstant());
        } catch (Exception e) {
            throw new Exception("time format should be (format: yyyy-MM-dd|HH:mm)");
        }
        if (timestamp.before(Timestamp.from(Instant.now())))
            throw new Exception("time already passed");
        return timestamp;
    }

    public static void assertInRange(int number, int min, int max, String parameterName) throws Exception {
        if (number < min || max < number)
            throw new Exception(String.format("%s should be from %d to %d", parameterName, min, max));
    }

    public Respond request(Callable<Respond> func) {
        try {
            return func.call();
        } catch (Exception e) {
            return new Respond(false, e.getMessage());
        }
    }
}
