package com.egrasoft.fxcommons.controller;

import com.egrasoft.fxcommons.service.FileManagerService;
import com.egrasoft.fxcommons.throwable.DataProcessingException;
import com.egrasoft.fxcommons.util.MessageHelper;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

import static com.egrasoft.fxcommons.util.ControllerUtils.*;

public abstract class FileWorkFrameController<DATA> {
    private Property<File> currentFileProperty = new SimpleObjectProperty<>();
    private BooleanProperty hasUnsavedChangesProperty = new SimpleBooleanProperty(false);
    private BooleanProperty hasFileProperty = new SimpleBooleanProperty(false);
    private MessageHelper messageHelper;

    public FileWorkFrameController(Stage stage, MessageHelper messageHelper) {
        this.messageHelper = messageHelper;

        Platform.setImplicitExit(false);
        stage.setOnCloseRequest(this::closeWindow);
    }

    protected boolean save() {
        if (hasFile() && hasUnsavedChanges()) {
            if (getCurrentFile() != null)
                return saveInternal(getCurrentFile());
            else
                return saveAs();
        }
        return false;
    }

    protected boolean saveAs() {
        if (hasFile()) {
            File file = createFileChooser(messageHelper.getSaveFileDialogTitle()).showSaveDialog(null);
            return file != null && saveInternal(file);
        }
        return false;
    }

    protected void openFile() {
        if (!canProceed())
            return;
        File file = createFileChooser(messageHelper.getOpenFileDialogTitle()).showOpenDialog(null);
        if (file != null)
            openInternal(file);
    }

    protected void closeFile() {
        if (!canProceed())
            return;
        currentFileProperty.setValue(null);
        markChanged(false);
        markHasFile(false);
        onFileClosed();
    }

    protected void newFile() {
        if (!canProceed())
            return;
        currentFileProperty.setValue(null);
        markChanged(true);
        markHasFile(true);
        onFileNew();
    }

    protected File getCurrentFile() {
        return currentFileProperty.getValue();
    }

    protected boolean hasUnsavedChanges() {
        return hasUnsavedChangesProperty.get();
    }

    protected boolean hasFile() {
        return hasFileProperty.get();
    }

    protected final void markChanged(boolean value) {
        hasUnsavedChangesProperty.set(value);
    }

    protected final void closeWindow() {
        if (canProceed())
            Platform.exit();
    }

    private void closeWindow(WindowEvent event) {
        if (canProceed())
            Platform.exit();
        else
            event.consume();
    }

    private void markHasFile(boolean value) {
        hasFileProperty.set(value);
    }

    private boolean saveInternal(File file) {
        try {
            getFileManagerService().save(getCurrentData(), file);
            currentFileProperty.setValue(file);
            markChanged(false);
            onFileSaved();
            return true;
        } catch (IOException exc) {
            createMessageDialog(Alert.AlertType.ERROR,
                    messageHelper.getSaveFileErrorDialogTitle(),
                    messageHelper.getSaveFileErrorDialogText()).showAndWait();
            exc.printStackTrace();
            return false;
        }
    }

    private void openInternal(File file) {
        try {
            DATA data = getFileManagerService().read(file);
            currentFileProperty.setValue(file);
            markChanged(false);
            markHasFile(true);
            onFileOpened(data);
        } catch (IOException exc) {
            createMessageDialog(Alert.AlertType.ERROR,
                    messageHelper.getOpenFileErrorDialogTitle(),
                    messageHelper.getOpenFileErrorDialogText()).showAndWait();
            exc.printStackTrace();
        } catch (DataProcessingException exc) {
            createMessageDialog(Alert.AlertType.ERROR,
                    messageHelper.getDataProcessingErrorDialogTitle(),
                    messageHelper.getDataProcessingErrorDialogText(exc.getMessageParameters())).showAndWait();
            exc.printStackTrace();
        }
    }

    private boolean canProceed() {
        if (!hasFile() || !hasUnsavedChanges())
            return true;

        ButtonBar.ButtonData userChoice = createAskForSavingDialog(messageHelper.getAskForSavingDialogTitle(),
                messageHelper.getAskForSavingDialogText(),
                messageHelper.getAskForSavingOptionYes(),
                messageHelper.getAskForSavingOptionNo(),
                messageHelper.getAskForSavingOptionCancel())
                .showAndWait()
                .map(ButtonType::getButtonData)
                .orElse(ButtonBar.ButtonData.CANCEL_CLOSE);
        return userChoice != ButtonBar.ButtonData.CANCEL_CLOSE && (userChoice != ButtonBar.ButtonData.YES || save());
    }

    protected abstract FileManagerService<DATA> getFileManagerService();

    protected abstract DATA getCurrentData();

    protected abstract void onFileSaved();

    protected abstract void onFileOpened(DATA data);

    protected abstract void onFileClosed();

    protected abstract void onFileNew();
}
