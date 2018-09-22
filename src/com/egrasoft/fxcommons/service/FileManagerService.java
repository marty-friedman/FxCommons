package com.egrasoft.fxcommons.service;

import java.io.File;
import java.io.IOException;

public interface FileManagerService<DATA> {
    void save(DATA data, File file) throws IOException;

    DATA read(File file) throws IOException;
}
