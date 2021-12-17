package controller;

import model.dao.BoardDao;
import model.dao.ParticipantDao;
import model.domain.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceController extends Controller {
    /* Static Methods */
    public static Workspace getWorkspace(User user) throws Exception {
        return new Workspace(user, BoardDao.getBoardsByLeader(user), ParticipantDao.getBoardsByContributor(user));
    }

    private static Board assertBoardExists(Workspace workspace, String nameOrNumber) throws Exception {
        List<Board> boards = new ArrayList<>(workspace.getLeaderBoards());
        boards.addAll(workspace.getGuestBoards());
        try {
            int num = Integer.parseInt(nameOrNumber);
            if (num - 1 < boards.size() && num > 0) return boards.get(num - 1);
        } catch (Exception e) {
            return boards.stream().filter(board -> board.getName().equals(nameOrNumber)).findFirst().orElse(null);
        }
        throw new Exception(String.format("There is no board (%s)", nameOrNumber));
    }

    /* Instance Fields */
    public Respond getWorkspaceNew(User loggedInUser) throws Exception {
        return new Respond(true, getWorkspace(loggedInUser));
    }

    public Respond createBoard(User loggedInUser, String boardName) throws Exception {
        Board board ;
        Participant participant;
        Workspace workspace = getWorkspace(loggedInUser);

        board = new Board(workspace.getUser(), boardName, Timestamp.from(Instant.now()), new ArrayList<>(), "pending");
        BoardDao.save(board);

        participant = new Participant(workspace.getUser(), Timestamp.from(Instant.now()), false);
        board.getContributors().add(participant);
        ParticipantDao.save(participant, board);
        workspace.getLeaderBoards().add(0, board);
        return new Respond(true, String.format("board (%s) create successfully", boardName));
    }

    public Respond selectBoard(User loggedInUser, String boardNameOrNumber) throws Exception {
        Workspace workspace = getWorkspace(loggedInUser);
        Board selectedBoard = assertBoardExists(workspace, boardNameOrNumber);
        BoardController.assertAcceptedBoard(selectedBoard);
        return new Respond(true, selectedBoard);
    }

    public Respond removeBoard(User loggedInUser, String boardNameOrNumber) throws Exception {
        Workspace workspace = getWorkspace(loggedInUser);
        Board selectedBoard = assertBoardExists(workspace, boardNameOrNumber);
        authentication(workspace.getUser(), selectedBoard);
        BoardDao.delete(selectedBoard);
        workspace.getLeaderBoards().remove(selectedBoard);
        return new Respond(true, String.format("board (%s) removed successfully", selectedBoard.getName()));
    }

    public Respond changeName(User loggedInUser, String boardNameOrNumber, String newName) throws Exception {
        String previousName;
        Workspace workspace = getWorkspace(loggedInUser);
        Board selectedBoard = assertBoardExists(workspace, boardNameOrNumber);
        authentication(workspace.getUser(), selectedBoard);
        previousName = selectedBoard.getName();
        selectedBoard.setName(newName);
        BoardDao.update(selectedBoard);
        return new Respond(true, String.format("board's name changed from (%s) to (%s) successfully",
                previousName, selectedBoard.getName()));
    }

    public Respond quitBoard(User loggedInUser, String boardNameOrNumber) throws Exception {
        Workspace workspace = getWorkspace(loggedInUser);
        Board selectedBoard = assertBoardExists(workspace, boardNameOrNumber);
        if (selectedBoard.getLeader().getId() == workspace.getUser().getId())
            throw new Exception("you are leader of this board can't quit");
        ParticipantDao.delete(selectedBoard, workspace.getUser());
        workspace.getGuestBoards().remove(selectedBoard);
        return new Respond(true, String.format("board (%s) removed successfully", selectedBoard.getName()));
    }
}
