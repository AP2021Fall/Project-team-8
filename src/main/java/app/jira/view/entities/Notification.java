package app.jira.view.entities;


import app.jira.model.dao.NotificationDao;
import app.jira.model.domain.User;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.HoverAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Notification {
    public ListView listview;
    public TextField searchInput;
    public ArrayList<String> data = new ArrayList<>();
    public Label backButton;

    @FXML
    public void initialize(){
        HoverAnimation.setAnimation(backButton, HoverAnimation.Mode.Hue, -0.1);
        setList();
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            updateList(newValue);
        });
    }

    private void setList() {
        try {
//            User user = UserDao.getByUsername("mobin");
            User user = Window.getInstance().getUser();
            ArrayList<app.jira.model.domain.Notification> nt = NotificationDao.getNotifications(user);
            data = new ArrayList<>();
            for (app.jira.model.domain.Notification notification : nt) {
                String item = "";
                //String item = "from : ";
                item += notification.getFrom();
                //item += "  Message : ";
                item += "\nMessage : " + notification.getText();
                System.out.println(item);
                listview.getItems().add(item);
                data.add(item);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void updateList(String newValue) {
        Pattern pattern = Pattern.compile(newValue);
        Matcher matcher;
        listview.getItems().clear();
        for (String s : data) {
            matcher = pattern.matcher(s);
            if(matcher.find())
                listview.getItems().add(s);
        }
    }

    public void back(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("profile"));
    }
}
