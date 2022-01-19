package app.jira.view;

import app.jira.App;

import java.io.File;
import java.util.Objects;

public class Config {
    // Static Fields
    public static final String APP_TITLE = "Jira";
    public static final String PROFILE_PIC = "E:\\work\\practice\\java\\snake_game\\src\\main\\resources\\game\\snake_game\\profile_pictures\\";
    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 800;

    // Static Methods
    public static String getProfilePic(int id) {
        String res = Objects.requireNonNull(App.class.getResource("profile_picture/default_profile.png")).toExternalForm();
        try {
            File file = new File(PROFILE_PIC + id + ".png");
            if (!file.exists()) return res;
            return file.toURI().toURL().toExternalForm();
        } catch (Exception e) {
            return res;
        }
    }
}
