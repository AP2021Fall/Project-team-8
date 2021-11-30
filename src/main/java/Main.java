import model.dao.DataBase;
import view.Window;

public class Main {
    public static void main(String[] args) {
        // Run database
        DataBase.getInstance().run();

        // Run window
        Window.getInstance().run();

        // Close database
        DataBase.getInstance().close();
    }
}
