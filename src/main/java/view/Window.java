package view;

import view.entities.RegisterLoginView;

import java.util.LinkedList;

public class Window {
    /* Static Fields */
    private static Window window;

    /* Instance Fields */
    private final LinkedList<View> viewHistory = new LinkedList<>();

    /* Constructor */
    private Window() {
        // Singleton class
        // Init first view
        viewHistory.push(new RegisterLoginView(this));
    }

    /* Static methods */
    // Get single instance of Controller
    public static Window getInstance() {
        if (Window.window == null)
            Window.window = new Window();
        return Window.window;
    }

    /* Getters And Setters */
    public View getView() {
        return viewHistory.peekLast();
    }

    /* Instance Methods */
    public void pushView(View newView) {
        viewHistory.addLast(newView);
    }

    public void popView() {
        viewHistory.removeLast();
    }

    // Main loop to run window view
    public void run() {
        System.out.println("welcome to our app\nIf you stock just call help");
        // Main Loop
        while (getView() != null) {
            getView().run();
        }
        System.out.println("hope to see you soon *_*");
    }
}
