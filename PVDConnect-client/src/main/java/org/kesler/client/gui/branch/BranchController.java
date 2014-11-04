package org.kesler.client.gui.branch;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.kesler.client.domain.Branch;
import org.kesler.client.gui.AbstractController;


public class BranchController extends AbstractController {

    private Branch branch;

    @FXML protected TextField codeTextField;
    @FXML protected TextArea  nameTextArea;
    @FXML protected TextField pvdIpTextField;
    @FXML protected TextField pvdUserTextField;
    @FXML protected PasswordField pvdPasswordField;

    @FXML
    protected void initialize() {

    }

    public void initBranch(Branch branch) {
        this.branch = branch;
    }

    @Override
    protected void updateContent() {
        codeTextField.setText(branch.getCode());
        nameTextArea.setText(branch.getName());
        pvdIpTextField.setText(branch.getPvdIp());
        pvdUserTextField.setText(branch.getPvdUser());
        pvdPasswordField.setText(branch.getPvdPassword());
    }

    @Override
    protected void updateResult() {
        branch.setCode(codeTextField.getText());
        branch.setName(nameTextArea.getText());
        branch.setPvdIp(pvdIpTextField.getText());
        branch.setPvdUser(pvdUserTextField.getText());
        branch.setPvdPassword(pvdPasswordField.getText());
    }

}
