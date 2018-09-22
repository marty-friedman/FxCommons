package com.egrasoft.fxcommons.util;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ControllerUtils {
    public static Alert createMessageDialog(Alert.AlertType type, String title, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText(null);
        return alert;
    }

    public static FileChooser createFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        return fileChooser;
    }

    public static Alert createAskForSavingDialog(String title, String text, String optionYes, String optionNo, String optionCancel) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText(null);
        alert.getButtonTypes().setAll(new ButtonType(optionYes, ButtonBar.ButtonData.YES),
                new ButtonType(optionNo, ButtonBar.ButtonData.NO),
                new ButtonType(optionCancel, ButtonBar.ButtonData.CANCEL_CLOSE));
        return alert;
    }

    public static <S, T> void prepareTableColumn(TableColumn<S, T> column,
                                                 Function<? super S, T> getter,
                                                 BiConsumer<? super S, T> setter,
                                                 Consumer<TableColumn.CellEditEvent> editHandler,
                                                 StringConverter<T> stringConverter) {
        column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(getter.apply(cellData.getValue())));
        column.setCellFactory(TextFieldTableCell.forTableColumn(stringConverter));
        column.setOnEditCommit(event -> {
            if (setter != null)
                setter.accept(event.getRowValue(), event.getNewValue());
            if (editHandler != null)
                editHandler.accept(event);
        });
    }

    public static <S> void markPresentRowsWithBorders(TableView<S> tableView, Integer borderWidth, String borderColor) {
        tableView.setRowFactory(row -> new TableRow<S>() {
            @Override
            protected void updateItem(S item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty)
                    setStyle("");
                else
                    setStyle("-fx-border-width: 0 0 " + borderWidth + " 0; -fx-border-color: " + borderColor + ";");
            }
        });
    }
}