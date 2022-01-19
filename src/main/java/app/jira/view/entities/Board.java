package app.jira.view.entities;

import app.jira.controller.BoardController;
import app.jira.controller.WorkspaceController;
import app.jira.model.domain.Participant;
import app.jira.model.domain.Respond;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class Board extends PageController {
    /* Static Fields */
    private static final BoardController controller = new BoardController();
    private static final WorkspaceController workspaceController = new WorkspaceController();

    /* Instance Methods */
    private double oldWidth = 0;
    private Invite inviteDiv;

    /* FXML Fields */
    @FXML
    public TextField boardName;
    public HBox listsPane;
    public AnchorPane sceneRoot;
    public ScrollPane scrollPane;
    public HBox members;
    public Button inviteBtn, roadmapBtn;
    public Alert alert;

    /* Getters */
    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public HBox getListsPane() {
        return listsPane;
    }

    /* Instance Methods */
    private void expandableText() {
        oldWidth = boardName.getMinWidth();
        boardName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (oldValue.length() != newValue.length()) {
                    oldWidth += (newValue.length() - oldValue.length()) * 10;
                    boardName.setPrefWidth(oldWidth);
                }
            }
        });
    }

    /* FXML Methods */
    // Initialization
    public void initialize() {
        inviteDiv = new Invite(sceneRoot, this);
        sceneRoot.setOnMouseClicked(mouseEvent -> inviteDiv.close(mouseEvent));
        HoverAnimation.setAnimation(inviteBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(roadmapBtn, HoverAnimation.Mode.Hue, -0.1);
        expandableText();
        setMembers();
        setName();
        setLists();
        changeBoardName();
        showRoadmap();
    }


    // Actions
    public void changeBoardName() {
        boardName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    if (!boardName.getText().equals(Window.getInstance().getBoard().getName())) {
                        Respond respond = controller.request(() -> workspaceController.changeName(Window.getInstance().getUser(), Window.getInstance().getBoard().getName(), boardName.getText()));
                        Window.getInstance().getBoard().setName(boardName.getText());
                        // Now Handle Respond
                        System.out.println(respond.getMessage());
                    }
                }
            }
        });
    }

    public void setName() {
        boardName.setText(Window.getInstance().getBoard().getName());
    }

    public void setLists() {
        List comList;
        Card card;
        Respond respond = controller.request(() -> controller.getLists(Window.getInstance().getBoard()));
        listsPane.getChildren().clear();

        // Now Handle Respond
        if (respond.isSuccess()) {
            ArrayList<app.jira.model.domain.List> lists = (ArrayList<app.jira.model.domain.List>) respond.getContent();
            for (app.jira.model.domain.List list : lists) {
                comList = new List(this, list);
                comList.tasks.getChildren().clear();
                listsPane.getChildren().add(comList);
                new DraggableList(comList, sceneRoot);
                for (app.jira.model.domain.Task task : list.getTasks()) {
                    card = new Card(comList, task, this);
                    comList.tasks.getChildren().add(card);
                    new DraggableCard(card, sceneRoot);
                }
            }
        } else {
            System.out.println(respond.getMessage());
        }
        addList();
    }

    public void addList() {
        TextInput textInput = new TextInput("Add Another List", "Save");
        listsPane.getChildren().add(textInput);
        textInput.saveBtn.setOnAction(mouseEvent -> {
            Respond respond = controller.request(() -> controller.addList(Window.getInstance().getUser(), Window.getInstance().getBoard(), textInput.textArea.getText(), null));
            // Now Handle Respond
            textInput.textArea.setText("");
            System.out.println(respond.getMessage());
            setLists();
        });
    }

    public void setMembers() {
        members.getChildren().clear();
        ProfilePicture profilePicture;
        // Set Members
        for (Participant contributor : Window.getInstance().getBoard().getContributors()) {
            profilePicture = new ProfilePicture(contributor.getUser(), this);
            ProfilePicture finalProfilePicture = profilePicture;
            profilePicture.setOnMouseClicked(mouseEvent -> {
                ProfileInvite profileInvite = new ProfileInvite(this, contributor.getUser());
                profileInvite.closeOthers();
                profileInvite.setLayoutX(finalProfilePicture.localToScene(finalProfilePicture.getBoundsInLocal()).getMinX());
                profileInvite.setLayoutY(finalProfilePicture.localToScene(finalProfilePicture.getBoundsInLocal()).getMaxY() + 10);
                this.sceneRoot.getChildren().add(profileInvite);
                profileInvite.close.setOnMouseClicked(mouseEvent1 -> this.sceneRoot.getChildren().remove(profileInvite));
            });
            members.getChildren().add(finalProfilePicture);
        }
    }

    public void showRoadmap() {
        roadmapBtn.setOnMouseClicked(mouseEvent -> {
            sceneRoot.getChildren().add(new Roadmap(this));
        });
    }

    public void back() {
        Window.getInstance().paginate(new Page("board-control"));
    }

    public void invite() {
        inviteDiv.setLayoutX(inviteBtn.getLayoutX());
        inviteDiv.setLayoutY(inviteBtn.getLayoutY() + inviteBtn.getHeight() + 15);
        sceneRoot.getChildren().add(inviteDiv);
    }
}
