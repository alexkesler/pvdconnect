package org.kesler.client.gui.branch;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.kesler.client.domain.Branch;
import org.kesler.client.gui.AbstractController;
import org.kesler.client.service.BranchService;
import org.kesler.client.util.FXUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class BranchListController extends AbstractController{

    protected static Logger log = LoggerFactory.getLogger(BranchListController.class);

    @Autowired
    private BranchService branchService;
    private ObservableList<Branch> observableBranches;

    @Autowired
    private BranchController branchController;

    @FXML protected ListView<Branch> branchListView;
    @FXML protected ProgressIndicator updateProgressIndicator;


    @FXML
    protected void initialize() {
        branchListView.setCellFactory(new Callback<ListView<Branch>, ListCell<Branch>>() {
            @Override
            public ListCell<Branch> call(ListView<Branch> param) {
                return new CertRightListCell();
            }
        });
        observableBranches = FXCollections.observableArrayList();
        branchListView.setItems(observableBranches);

    }

    @Override
    public void updateContent() {
        observableBranches.clear();
        Task<Collection<Branch>> updateTask = new Task<Collection<Branch>>() {
            @Override
            protected Collection<Branch> call() throws Exception {
                log.debug("Updating branches...");
                return branchService.getAllBranches();
            }

            @Override
            protected void succeeded() {
                Collection<Branch> branches = getValue();
                log.debug("Server return " + branches.size() + " branches");
                observableBranches.addAll(branches);
            }

            @Override
            protected void failed() {
                Throwable exception = getException();
                log.error("Error updating: " + exception, exception);
            }
        };

        BooleanBinding runningBinding = updateTask.stateProperty().isEqualTo(Task.State.RUNNING);

        updateProgressIndicator.visibleProperty().bind(runningBinding);

        new Thread(updateTask).start();

    }

    // Обработчики кнопок управления списком сотрудников
    @FXML
    protected void handleAddBranchButtonAction(ActionEvent ev) {
        addBranch();
    }

    @FXML
    protected void handleEditBranchButtonAction(ActionEvent ev) {
        editBranch();
    }

    @FXML
    protected void handleBranchListViewMouseClick(MouseEvent ev) {
        if (ev.getClickCount()==2) {
            editBranch();
        }
    }

    @FXML
    protected void handleRemoveBranchButtonAction(ActionEvent ev) {
        removeBranch();
    }



    // Методы управления списком сотрудников

    private void addBranch() {
        log.info("Opening add Branch dialog");

        Branch newBranch = new Branch();

        branchController.initBranch(newBranch);
        branchController.showAndWait(stage);
        if (branchController.getResult() == AbstractController.Result.OK) {
            log.info("Saving Branch: " + newBranch.getName());
            observableBranches.add(newBranch);
            branchService.addBranch(newBranch);
            branchListView.getSelectionModel().select(observableBranches.indexOf(newBranch));
        }

    }

    private void editBranch() {
        int selectedIndex = branchListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex<0) {
            Dialogs.create()
                    .owner(root.getScene().getWindow())
                    .title("Внимание")
                    .message("Ничего не выбрано")
                    .showWarning();
            return;
        }

        Branch selectedBranch = observableBranches.get(selectedIndex);

        branchController.initBranch(selectedBranch);
        branchController.showAndWait(stage);
        if (branchController.getResult() == AbstractController.Result.OK) {
            branchService.updateBranch(selectedBranch);
            FXUtils.triggerUpdateListView(branchListView, selectedBranch, selectedIndex);
            branchListView.getSelectionModel().select(selectedIndex);
        }

    }


    private void removeBranch() {
        Branch selectedBranch = branchListView.getSelectionModel().getSelectedItem();
        if (selectedBranch ==null) {
            Dialogs.create()
                    .owner(root.getScene().getWindow())
                    .title("Внимание")
                    .message("Ничего не выбрано")
                    .showWarning();
            return;
        }

        Action response = Dialogs.create()
                .owner(root.getScene().getWindow())
                .title("Подтверждение")
                .message("Удалить выбранный филиал: " + selectedBranch.getName())
                .showConfirm();
        if (response == Dialog.ACTION_YES) {
            observableBranches.remove(selectedBranch);
            branchService.removeBranch(selectedBranch);
        }
    }


    // Вспомогательные классы для списка сотрудников
    class CertRightListCell extends ListCell<Branch> {
        @Override
        protected void updateItem(Branch item, boolean empty) {
            super.updateItem(item, empty);
            setText(item==null?"":(item.getName()==null?"":item.getName()));
        }
    }

 }
