package app.jira.view;

import app.jira.App;

import java.io.File;
import java.util.Objects;

public class Config {
    // Static Fields
    public static final String APP_TITLE = "Jira";
    public static final String PROFILE_PIC = "D:\\work\\project\\Project-team-8\\src\\main\\resources\\com\\example\\jira_ui\\profile_pictures\\";
    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 800;

    // Static Methods
    public static String getProfilePic(int id) {
        String res = Objects.requireNonNull(App.class.getResource("profile_pictures/default-profile.jpg")).toExternalForm();
        try {
            File file = new File(PROFILE_PIC + id + ".jpg");
            if (!file.exists()) return res;
            return file.toURI().toURL().toExternalForm();
        } catch (Exception e) {
            return res;
        }
    }
}
