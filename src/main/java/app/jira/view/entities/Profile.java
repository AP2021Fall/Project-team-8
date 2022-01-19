package app.jira.view.entities;

import app.jira.model.dao.UserDao;
import app.jira.model.domain.User;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.HoverAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

public class Profile extends PageController {
    public static String lastMenu = "main-menu";
    public User user;
    public Label backButton;
    public Button changePicBtn;
    public Button showLogBtn;
    public Button showTeamsBtn;
    public Button changePassBtn;
    public Button changeUserBtn;
    public ImageView profile_image;
    public ImageView notification;
    public Label usernameField;
    public Label emailField;
    public Label dateField;

    public void back(MouseEvent mouseEvent) {
        if (lastMenu.equals("users-list")) {
            try {
                Window.getInstance().setUser(UserDao.getByUsername("SystemAdmin"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        Window.getInstance().paginate(new Page(lastMenu));
    }

    @FXML
    public void initialize() {
        user = Window.getInstance().getUser();
        setProfileLabels();
        HoverAnimation.setAnimation(changePicBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(backButton, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(showLogBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(showTeamsBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(changePassBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(changeUserBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(notification, HoverAnimation.Mode.Hue, -0.1);
        InputStream stream = null;
        try {
            stream = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/app/jira/icons/notification.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(stream);
        notification.setImage(image);
        setProfileImage(profile_image, System.getProperty("user.dir") + "/src/main/resources/app/jira/profile_pictures/" + user.getId() + ".jpg");
    }

    private void setProfileLabels() {
        emailField.setText(user.getEmail());
        usernameField.setText(user.getUsername());
        dateField.setText(String.valueOf(user.getBirthday()));
    }

    public void showLog(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("login-activity"));
    }

    public void showBoards(MouseEvent mouseEvent) {
        Workspace.lastMenu = "profile";
        Window.getInstance().paginate(new Page("workspace"));
    }

    public void changePass(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("change-password"));
    }

    public void changeUser(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("change-username"));
    }

    public void openFileChooser(MouseEvent mouseEvent) throws IOException {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        fc.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File file = fc.showOpenDialog(null);
        String finalPath = System.getProperty("user.dir");
        File finalFile = new File(finalPath);
        finalPath = finalFile.getPath();
        finalPath += "/src/main/resources/app/jira/profile_pictures/";
        finalPath += user.getId();
        finalPath += ".jpg";
        finalFile = new File(finalPath);
        if (file != null && file.exists()) {
            if (finalFile.exists()) {
                //ProfileController.setBasicImage(profile_image);
                Files.deleteIfExists(Path.of(finalPath));
            }
            System.out.println(finalPath);
            System.out.println(file.toPath());
            Files.copy(file.toPath(), new File(finalPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
            setProfileImage(profile_image, finalPath);
        }

    }

    private void setProfileImage(ImageView profile_image, String path) {
        File finalFile = new File(path);
        if (!finalFile.exists()) {
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

    public void notificationMenu(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("notification"));
    }
}
