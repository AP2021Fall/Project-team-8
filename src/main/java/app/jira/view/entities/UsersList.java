package app.jira.view.entities;

import app.jira.model.dao.UserDao;
import app.jira.model.domain.User;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.HoverAnimation;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsersList extends PageController{
    public ListView listview;
    public ArrayList<User> users = new ArrayList<>();
    public TextField searchInput;
    public Label backButton;
    private ArrayList<HBox> items = new ArrayList<>();
    @FXML
    public void initialize() {
        setUsers();
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            updateList(newValue);
        });
        HoverAnimation.setAnimation(backButton, HoverAnimation.Mode.Hue, -0.1);
    }

    private void updateList(String newValue) {
        if(newValue.equals("")) {
            listview.getItems().clear();
            setUsers();
        }
        else {
            listview.getItems().clear();
            Label title1 = new Label("Username");
            Label title2 = new Label("ID");
            title1.setPrefWidth(150);
            title2.setPrefWidth(150);
            HBox titles = new HBox(title1, title2);
            listview.getItems().add(titles);
            Pattern pattern = Pattern.compile(newValue);
            Matcher matcher = null;
            for (HBox item : items) {
                Label username = (Label) item.getChildren().get(0);
                Label id = (Label) item.getChildren().get(1);
                matcher = pattern.matcher(username.getText());
                if (matcher.find())
                    listview.getItems().add(item);
                matcher = pattern.matcher(id.getText());
                if (matcher.find())
                    listview.getItems().add(item);
            }
        }
    }

    private void setUsers() {
        items.clear();
        Label title1 = new Label("Username");
        Label title2 = new Label("ID");
        title1.setPrefWidth(150);
        title2.setPrefWidth(150);
        HBox titles = new HBox(title1,title2);
        listview.getItems().add(titles);
        try {
            users = UserDao.getAllUsers();
            for (User user : users) {
                System.out.println(user.getUsername()+" "+user.getName());
                Label username = new Label(user.getUsername());
                username.setPrefWidth(150);
                Label id = new Label(String.valueOf(user.getId()));
                id.setPrefWidth(150);
                HBox item = new HBox(username,id);
                item.setMaxWidth(1000);
                item.setId(user.getUsername());
                item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        showProfile(item.getId());
                    }
                });
                listview.getItems().add(item);
                items.add(item);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void showProfile(String id) {
        Profile.lastMenu = "users-list";
        try {
            Window.getInstance().setUser(UserDao.getByUsername(id));
            Window.getInstance().paginate(new Page("profile"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void back(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("admin-menu"));
    }
}
