package app.jira.view.entities;

import app.jira.controller.AdminController;
import app.jira.model.domain.Board;
import app.jira.model.domain.Respond;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.HoverAnimation;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class AcceptBoard extends PageController {
    public ListView listview;
    public TextField searchInput;
    public Label backButton;
    public Button rejectBtn;
    public Button acceptBtn;
    private String selectedBoard;
    private AdminController controller = new AdminController();
    private ArrayList<Board> boards = new ArrayList<>();
    @FXML
    public void initialize() {
        HoverAnimation.setAnimation(rejectBtn, HoverAnimation.Mode.Hue, -0.3);
        HoverAnimation.setAnimation(acceptBtn, HoverAnimation.Mode.Hue, -0.3);
        HoverAnimation.setAnimation(acceptBtn, HoverAnimation.Mode.Hue, -0.3);
        setBoards();
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            updateList(newValue);
        });
    }

    private void updateList(String newValue) {

    }

    private void setBoards() {
        listview.getItems().clear();
        try {
            boards = (ArrayList<Board>) controller.getPendingBoards(Window.getInstance().getUser()).getContent();
            for (Board board : boards) {
                Label info = new Label("Board Name: " + board.getName() + "          ID: " + board.getId());
                info.setId(String.valueOf(board.getId()));
                info.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectedBoard= info.getId();
                    }
                });
                listview.getItems().add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void back(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("admin-menu"));
    }

    public void reject(MouseEvent mouseEvent) {
        if(selectedBoard!=null)
            if(!selectedBoard.equals("")){
            try {
                Respond respond = controller.rejectBoards(Window.getInstance().getUser(), selectedBoard);
                selectedBoard = "";
                setBoards();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void accept(MouseEvent mouseEvent) {
        if(selectedBoard!=null)
            if(!selectedBoard.equals("")){
            try {
                Respond respond = controller.acceptBoards(Window.getInstance().getUser(), selectedBoard);
                selectedBoard = "";
                setBoards();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
