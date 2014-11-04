package org.kesler.client.gui.check;


import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import org.kesler.client.domain.Check;
import org.kesler.client.gui.AbstractController;
import org.kesler.client.service.CheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ChecksController extends AbstractController{
    private static final Logger log = LoggerFactory.getLogger(ChecksController.class);


    @FXML protected TableView<Check> checksTableView;
    @FXML protected ProgressIndicator updateProgressIndicator;
    @FXML protected Button updateButton;

    @Autowired
    private CheckService checkService;

    @FXML protected void initialize() {

    }


    @FXML protected void handleUpdateButtonAction(ActionEvent ev) {
        updateContent();
    }


    @Override
    protected void updateContent() {
        log.info("Update Checks");
        Task<Collection<Check>> checkUpdater = new Task<Collection<Check>>() {
            @Override
            protected Collection<Check> call() throws Exception {
                return checkService.getAllChecks();
            }

            @Override
            protected void succeeded() {
                ObservableList<Check> observableChecks = FXCollections.observableArrayList(getValue());
                checksTableView.setItems(observableChecks);
                log.info("List updated: " + observableChecks.size() + "checks");
            }

            @Override
            protected void failed() {
                Throwable exception = getException();
                log.error("update error: " + exception, exception);
            }
        };

        BooleanBinding runningBinding = checkUpdater.stateProperty().isEqualTo(Task.State.RUNNING);
        updateProgressIndicator.visibleProperty().bind(runningBinding);
        updateButton.disableProperty().bind(runningBinding);

        new Thread(checkUpdater).start();
    }
}
