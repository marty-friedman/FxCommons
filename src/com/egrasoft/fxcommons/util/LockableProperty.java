package com.egrasoft.fxcommons.util;

import javafx.beans.property.SimpleObjectProperty;

public class LockableProperty<T> extends SimpleObjectProperty<T> {
    private boolean locked = false;

    public LockableProperty() {
    }

    public LockableProperty(T initialValue) {
        super(initialValue);
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public void setValue(T value) {
        if (!locked)
            super.setValue(value);
    }

    @Override
    public void set(T newValue) {
        if (!locked)
            super.set(newValue);
    }
}
