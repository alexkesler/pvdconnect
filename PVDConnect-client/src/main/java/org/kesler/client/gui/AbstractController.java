package org.kesler.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController {
    protected static Logger log = LoggerFactory.getLogger(AbstractController.class);
    @FXML protected Parent root;
    protected Stage stage;

    public enum Result{
        OK,
        CANCEL,
        NONE
    }

    protected Result result = Result.NONE;

    public Result getResult() {
        return result;
    }

    public Parent getRoot() { return root; }

    public void show(Window owner) {
        log.info("Show view");
        if (stage==null) {
            stage = new Stage();
            stage.initOwner(owner);
            stage.setScene(new Scene(root));
        }
        stage.show();
        updateContent();
    }

    public void showAndWait(Window owner) {
        log.info("Show view and wait");
        if (stage==null) {
            stage = new Stage();
            stage.initOwner(owner);
            stage.setScene(new Scene(root));
        }

        updateContent();
        stage.showAndWait();
    }

    protected void updateContent() {}

    protected void updateResult() {}


    @FXML protected void handleCloseButtonAction(ActionEvent ev) {
        log.info("Close button pressed - hide view");
        result = Result.NONE;
        root.getScene().getWindow().hide();
    }

    @FXML protected void handleOkButtonAction(ActionEvent ev) {
        log.info("Ok button pressed - saving and hide view");
        result = Result.OK;
        updateResult();
        root.getScene().getWindow().hide();
    }

    @FXML protected void handleCancelButtonAction(ActionEvent ev) {
        log.info("Cancel button pressed - show confirm dialog");
        Action response = Dialogs.create()
                .owner(root.getScene().getWindow())
                .title("Внимание")
                .message("Закрыть без сохранения?")
                .actions(Dialog.ACTION_YES,Dialog.ACTION_NO)
                .showConfirm();
        if (response == Dialog.ACTION_YES) {
            log.info("Confirmed cancel - hide view");
            result = Result.CANCEL;
            root.getScene().getWindow().hide();
        } else {
            log.info("Cancel not confirmed");
        }

    }


}
