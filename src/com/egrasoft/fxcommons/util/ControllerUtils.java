package com.egrasoft.fxcommons.util;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.util.Map;
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

    public static TextInputDialog createTextInputDialog(String title, String text, String defaultValue) {
        defaultValue = defaultValue != null ? defaultValue : "";
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle(title);
        dialog.setContentText(text);
        dialog.setHeaderText(null);
        return dialog;
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
        if (stringConverter != null)
            column.setCellFactory(TextFieldTableCell.forTableColumn(stringConverter));
        column.setOnEditCommit(event -> {
            if (setter != null)
                setter.accept(event.getRowValue(), event.getNewValue());
            if (editHandler != null)
                editHandler.accept(event);
        });
    }

    public static <S> void prepareListView(ListView<S> listView, Function<? super S, String> mapper) {
        listView.setCellFactory(lv -> new ListCell<S>() {
            @Override
            protected void updateItem(S item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(mapper.apply(item));
                }
            }
        });
    }

    public static <T> T performSafe(ExceptionProneSupplier<T> function, ErrorMap errorMap, String errorTitle) {
        try {
            return function.run();
        } catch (Exception e) {
            Class<? extends Exception> excClass = e.getClass();
            e.printStackTrace();
            if (errorMap.containsKey(excClass))
                createMessageDialog(Alert.AlertType.ERROR, errorTitle, errorMap.get(excClass)).showAndWait();
            else
                errorMap.entrySet().stream()
                        .filter(entry -> entry.getKey().isAssignableFrom(excClass))
                        .findFirst()
                        .map(Map.Entry::getValue)
                        .ifPresent(message ->
                                createMessageDialog(Alert.AlertType.ERROR, errorTitle, message).showAndWait());
            return null;
        }
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
