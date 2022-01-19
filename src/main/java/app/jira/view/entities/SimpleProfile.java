package app.jira.view.entities;

import app.jira.model.domain.User;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.HoverAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SimpleProfile extends PageController{

    public Label backButton;
    public ImageView profile_image;
    public Label usernameField;
    public Label emailField;
    public Label dateField;
    public static User user;
    @FXML
    public void initialize(){
        setProfileImage(profile_image,System.getProperty("user.dir")+"/src/main/resources/app/jira/profile_pictures/" + user.getId()+".jpg");
        HoverAnimation.setAnimation(backButton, HoverAnimation.Mode.Hue, -0.1);
        setProfileLabels();
    }
    private void setProfileImage(ImageView profile_image,String path) {
        File finalFile = new File(path);
        if(!finalFile.exists()) {
            path = System.getProperty("user.dir");
            path += "\\src\\main\\resources\\app\\jira\\images\\baseProfile.png";
        }
        InputStream stream = null;
        try {
            stream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(stream);
        profile_image.setImage(image);
    }

    public void back(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("chat-room"));
    }

    private void setProfileLabels() {
        emailField.setText(user.getEmail());
        usernameField.setText(user.getUsername());
        dateField.setText(String.valueOf(user.getBirthday()));
    }

}
