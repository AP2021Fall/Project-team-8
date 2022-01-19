package app.jira.view.entities;

import app.jira.controller.ChatController;
import app.jira.model.dao.ChatDao;
import app.jira.model.dao.UserDao;
import app.jira.model.domain.Chat;
import app.jira.model.domain.Respond;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.ChatItem;
import app.jira.view.components.HoverAnimation;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatRoom extends PageController {
    private static final ChatController controller = new ChatController();

    public ListView listview;
    public TextField searchInput;
    public Label backButton;
    public TextField senderBox;
    public Button sendBtn;

    @FXML
    public void initialize() {
        HoverAnimation.setAnimation(sendBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(backButton, HoverAnimation.Mode.Hue, -0.1);
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            updateList(newValue);
        });
        HoverAnimation.setAnimation(backButton, HoverAnimation.Mode.Hue, -0.1);
        setList();
    }

    private void updateList(String newValue) {

    }

    private void setList() {
        listview.getItems().clear();
        try {
            ArrayList<Chat> chats = ChatDao.getChatsByBoard(Window.getInstance().getBoard());
            for (Chat chat : chats) {
                ChatItem chatItem = new ChatItem();
                chatItem.chatItem.setText(chat.getMessage());
                chatItem.name.setText(chat.getSendBy().getUsername());
                chatItem.time.setText(chat.getSendAt().toString());
                chatItem.profileImage.setImage(getUserImage(String.valueOf(chat.getSendBy().getId())));
                chatItem.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        showProfile(chatItem.name.getText());
                    }
                });
                listview.getItems().add(chatItem);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void showProfile(String username) {
        try {
            SimpleProfile2.user = UserDao.getByUsername(username);
            Window.getInstance().paginate(new Page("simple-profile2"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Image getUserImage(String ID) {
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/app/jira/profile_pictures/" + ID + ".jpg");
        if (file.exists()) {
            InputStream stream = null;
            try {
                stream = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/app/jira/profile_pictures/" + ID + ".jpg");
                return new Image(stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            InputStream stream = null;
            try {
                stream = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/app/jira/images/baseProfile.png");
                return new Image(stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void back(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("board-control"));
    }

    public void sendMessage(MouseEvent mouseEvent) {
        if (senderBox.getText() != null)
            if (!senderBox.getText().equals("")) {
                Respond respond = controller.request(() -> controller.sendMessage(Window.getInstance().getUser(), Window.getInstance().getBoard(), senderBox.getText()));
                setList();
            }
    }
}
