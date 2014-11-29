package org.kesler.client.gui.cause;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Window;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.kesler.client.domain.Cause;
import org.kesler.client.domain.Record;
import org.kesler.client.gui.AbstractController;
import org.kesler.client.service.CauseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Component
public class CauseController extends AbstractController{
    private static Logger log = LoggerFactory.getLogger(CauseController.class);

    @FXML protected TextField branchTextField;
    @FXML protected TextField regnumTextField;
    @FXML protected TextField regdateTextField;
    @FXML protected TextField stateTextField;
    @FXML protected TextField statusMdTextField;
    @FXML protected TextField estimateDateTextField;
    @FXML protected TextField stateChangeDateTextField;
    @FXML protected TextField purposeTextField;
    @FXML protected ListView<String> applicatorsListView;
    @FXML protected TextField objTextField;
    @FXML protected TextField curStepTextField;
    @FXML protected ListView<String> stepsListView;
    @FXML protected ProgressIndicator updateProgressIndicator;

    private Cause cause;
    private Record record;

    @Autowired
    private CauseService causeService;

    @FXML protected void initialize() {
        root.prefHeight(500);
        root.prefWidth(900);
    }

    public void initRecord(Record record) {
        this.record = record;
    }

    @Override
    public void show(Window owner) {
        Image icon = new Image(CauseController.class.getResourceAsStream("/images/book_open2.png"));
        super.show(owner, "Дело ПК ПВД", icon);
    }

    @Override
    protected void updateContent() {
        clearView();
        Task<Cause> updateTask = new Task<Cause>() {
            @Override
            protected Cause call() throws Exception {
                return causeService.getCauseByRecord(record);
            }

            @Override
            protected void succeeded() {
                cause = getValue();
                updateView();
            }

            @Override
            protected void failed() {
                Throwable exception = getException();
                log.error("Error recieving Cause: " + exception, exception);
                Dialogs.create()
                        .owner(stage)
                        .title("Ошибка")
                        .message("Ошибка соединения с сервером ПК ПВД:" + exception.getMessage())
                        .showException(exception);
            }
        };

        BooleanBinding runningBinding = updateTask.stateProperty().isEqualTo(Task.State.RUNNING);
        updateProgressIndicator.visibleProperty().bind(runningBinding);

        new Thread(updateTask).start();
    }

    private void updateView() {
        if (cause==null) return;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        branchTextField.setText(cause.getRecord().getBranchName());
        regnumTextField.setText(cause.getRecord().getRegnum());
        regdateTextField.setText(dateFormat.format(cause.getRecord().getRegdate()));
        stateTextField.setText(cause.getStateString());
        statusMdTextField.setText(cause.getStatusMdString());
        estimateDateTextField.setText(dateFormat.format(cause.getEstimateDate()));
        stateChangeDateTextField.setText(dateFormat.format(cause.getStateChangeDate()));
        applicatorsListView.setItems(FXCollections.observableArrayList(cause.getApplicators()));
        purposeTextField.setText(cause.getPurposeString());
        objTextField.setText(cause.getObj());
        curStepTextField.setText(cause.getCurStep());
        stepsListView.setItems(FXCollections.observableArrayList(cause.getSteps()));

    }

    private void clearView() {
        branchTextField.setText("");
        regnumTextField.setText("");
        regdateTextField.setText("");
        stateTextField.setText("");
        statusMdTextField.setText("");
        estimateDateTextField.setText("");
        stateChangeDateTextField.setText("");
        purposeTextField.setText("");
        applicatorsListView.setItems(FXCollections.observableArrayList(new ArrayList<String>()));
        objTextField.setText("");
        curStepTextField.setText("");
        stepsListView.setItems(FXCollections.observableArrayList(new ArrayList<String>()));
    }

}
