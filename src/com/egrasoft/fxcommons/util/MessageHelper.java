package com.egrasoft.fxcommons.util;

public interface MessageHelper {
    default String getSaveFileDialogTitle() {
        return "";
    }

    default String getSaveFileErrorDialogTitle() {
        return "";
    }

    default String getSaveFileErrorDialogText() {
        return "";
    }

    default String getOpenFileDialogTitle() {
        return "";
    }

    default String getOpenFileErrorDialogTitle() {
        return "";
    }

    default String getOpenFileErrorDialogText() {
        return "";
    }

    default String getAskForSavingDialogTitle() {
        return "";
    }

    default String getAskForSavingDialogText() {
        return "";
    }

    default String getAskForSavingOptionYes() {
        return "";
    }

    default String getAskForSavingOptionNo() {
        return "";
    }

    default String getAskForSavingOptionCancel() {
        return "";
    }

    default String getDataProcessingErrorDialogTitle() {
        return "";
    }

    default String getDataProcessingErrorDialogText(String ... params) {
        return "";
    }
}
