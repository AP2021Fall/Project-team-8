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

    protected void back() {
        window.popView();
    }
}
