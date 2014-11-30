package org.kesler.client.gui.main;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.controlsfx.dialog.*;
import org.controlsfx.dialog.Dialog;
import org.kesler.client.domain.Record;
import org.kesler.client.gui.branch.BranchListController;
import org.kesler.client.gui.cause.CauseController;
import org.kesler.client.gui.check.ChecksController;
import org.kesler.client.service.CheckService;
import org.kesler.client.service.RecordService;
import org.kesler.common.ProgressStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    @FXML
    protected Parent root;
    @FXML protected TextField findTextField;
    @FXML protected Button findButton;
    @FXML protected ProgressIndicator findProgressIndicator;
    @FXML protected Button checkBranchesButton;
    @FXML protected TableView<Record> recordTableView;
    @FXML protected Label taskMessageLabel;
    @FXML protected ProgressIndicator updateProgressIndicator;
    @FXML protected Menu configMenu;

    private boolean config = false;

    private final ObservableList<Record> observableRecords = FXCollections.observableArrayList();

    @Autowired
    private RecordService recordService;

    @Autowired
    private CheckService checkService;

    @Autowired
    private BranchListController branchListController;

    @Autowired
    private ChecksController checksController;

    @Autowired
    private CauseController causeController;

    public Parent getRoot() {return root;}

    @FXML
    protected void initialize() {
        recordTableView.setItems(observableRecords);
    }

    public void setConfig(boolean config) {
        this.config = config;

        checkBranchesButton.setVisible(config);
        configMenu.setVisible(config);

    }

    @FXML protected void handleChecksMenuItemAction(ActionEvent ev) {
        checksController.show(root.getScene().getWindow());
    }

    @FXML protected void handleCloseMenuItemAction(ActionEvent ev) {
        root.getScene().getWindow().hide();
    }

    @FXML protected void handleBranchListMenuItemAction(ActionEvent ev) {

        branchListController.show(root.getScene().getWindow());

    }

    @FXML protected void handleFindRecordsByCodeButtonAction(ActionEvent ev) {
        findRecords();
    }

    @FXML protected void handleCheckBranchesButtonAction(ActionEvent ev) {
        checkBranches();
    }

    @FXML
    protected void handleRecordTableViewMouseClicked(MouseEvent ev) {
        if (ev.getClickCount()==2) {
            Record selectedRecord = recordTableView.getSelectionModel().getSelectedItem();
            if (selectedRecord!=null) {
                causeController.show(root.getScene().getWindow(), selectedRecord);
            } else {
                Dialogs.create()
                        .owner(root.getScene().getWindow())
                        .title("Внимание")
                        .message("НИчего не выбрано")
                        .showWarning();
            }
        }
    }


    private void findRecords() {
        final String code = findTextField.getText();
        if (code.isEmpty()) return;
        Task<Collection<Record>> findTask = new Task<Collection<Record>>() {
            @Override
            protected Collection<Record> call() throws Exception {
                return recordService.getRecordsByCode(code);
            }

            @Override
            protected void succeeded() {
                Collection<Record> records = getValue();
                log.debug("Server return " + records.size() + " records");
                observableRecords.clear();
                observableRecords.addAll(records);
            }

            @Override
            protected void failed() {
                Throwable exception = getException();
                log.error("Server returned error: " + exception, exception);
            }
        };

        BooleanBinding runningBinding = findTask.stateProperty().isEqualTo(Task.State.RUNNING);

        findTextField.disableProperty().bind(runningBinding);
        findButton.disableProperty().bind(runningBinding);
        findProgressIndicator.visibleProperty().bind(runningBinding);

        new Thread(findTask).start();

    }



    private void checkBranches() {

        final Task<Void> progressTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (!isCancelled()) {
                    try {Thread.sleep(500);} catch (Exception e) {}
                    ProgressStatus status = checkService.getProcessStatus();
                    updateMessage(status.getMessage());
                    updateProgress(status.getCurrent(),status.getTotal());
                }
                return null;
            }
        };

        Task<Void> checkTack = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                checkService.doChecks();
                return null;
            }

            @Override
            protected void succeeded() {
                log.info("Checking complete");
                progressTask.cancel();
            }

            @Override
            protected void failed() {
                Throwable exception = getException();
                log.error("Error checks: " + exception, exception);
                progressTask.cancel();
            }
        };

        BooleanBinding runningBinding = checkTack.stateProperty().isEqualTo(Task.State.RUNNING);

        checkBranchesButton.disableProperty().bind(runningBinding);
        updateProgressIndicator.visibleProperty().bind(runningBinding);

        taskMessageLabel.textProperty().bind(progressTask.messageProperty());
        updateProgressIndicator.progressProperty().bind(progressTask.progressProperty());

        new Thread(checkTack).start();
        new Thread(progressTask).start();

    }

}
