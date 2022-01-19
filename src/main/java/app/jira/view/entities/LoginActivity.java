package app.jira.view.entities;

import app.jira.model.dao.LogDao;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.HoverAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends PageController{

    public ScrollPane scrollPane;
    public Label backButton;

    public static ArrayList<String> log = new ArrayList<>();
    public ListView listview;
    public TextField searchInput;

    @FXML
    public void initialize() {
        setList();
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            updateList(newValue);
        });
        HoverAnimation.setAnimation(backButton, HoverAnimation.Mode.Hue, -0.1);
    }

    private void setList() {
        ArrayList<Timestamp> data = null;
        try {
             data = LogDao.getLogs(Window.getInstance().getUser());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for (Timestamp ts : data) {
            listview.getItems().add(ts.toString());
            log.add(ts.toString());
        }
    }

    private void updateList(String newValue) {
        ArrayList<String> found = new ArrayList<>();
        Pattern pattern = Pattern.compile(newValue);
        Matcher matcher;
        listview.getItems().clear();
        for (String s : log) {
            matcher = pattern.matcher(s);
            if(matcher.find())
                listview.getItems().add(s);
        }
    }

    public void back(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("profile"));
    }
}
