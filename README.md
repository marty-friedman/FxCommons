# FxCommons library

## Version v0.1: First stable version of the FxCommons library
Changelog:
```
- FileWorkFrameController - abstract controller which allows you to manage files (i.e. to save, open, close, ...)
- MessageHelper - helper interface which allows this library's classes to obtain messages which have to be shown to the user
- FileManagerService - generic interface which programmer should implement in order to have a possibility to use FileWorkFrameController, contains read/write methods supposed to manage file operations
- ControllerUtils - support class for javafx controllers, currently contains methods to create a message dialog, file chooser, ask-for-saving dialog and to add borders for table view present rows
```

