# FxCommons library

## Version v0.1.3: Overloaded methods for dialogs creation
Changelog:
```
- Added overloaded methods for different dialogs creation, allowing you to provide custom ok/cancel buttons' text
```

## Version v0.1.2: New dialog, safe performer, prepare ListView
Changelog:
```
- ControllerUtils - now can prepare ListView, perform operations safely and create text input prompt dialogs
- ErrorMap - Map<Class<? extends Exception>, String> interface used by performSafe method
- ExceptionProneSupplier<T> - contains function which should be performed by performSafe method
```

## Version v0.1.1: Implemented LockableProperty
Changelog:
```
- LockableProperty - can be locked so that set / setValue method calls are ignored
- FileWorkFrameController - improvements connected with adding a new Property, user now cannot make unsavedChangesProperty 'dirty' in listener methods
```

## Version v0.1: First stable version of the FxCommons library
Changelog:
```
- FileWorkFrameController - abstract controller which allows you to manage files (i.e. to save, open, close, ...)
- MessageHelper - helper interface which allows this library's classes to obtain messages which have to be shown to the user
- FileManagerService - generic interface which programmer should implement in order to have a possibility to use FileWorkFrameController, contains read/write methods supposed to manage file operations
- ControllerUtils - support class for javafx controllers, currently contains methods to create a message dialog, file chooser, ask-for-saving dialog and to add borders for table view present rows
```
