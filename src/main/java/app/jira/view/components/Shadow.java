package app.jira.view.components;

import javafx.scene.control.Label;

public class Shadow extends Label {
    /* Instance Fields */
    private List list;

    /* Constructor */
    public Shadow(List list) {
        this.list = list;
        this.setStyle("-fx-background-color: rgba(0,0,0,0.11);-fx-background-radius: 3px;");
    }

    /* Getters And Setters */
    public void setList(List list) {
        this.list = list;
    }

    public List getList() {
        return list;
    }
}
