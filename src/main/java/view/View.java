package view;

public abstract class View {
    /* Instance Fields */
    protected final Window window;

    /* Constructor */
    protected View(Window window) {
        this.window = window;
    }

    /* Instance Methods */
    public abstract void run();

    public void back() {
        window.popView();
    }
}
