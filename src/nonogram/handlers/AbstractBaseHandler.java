package nonogram.handlers;

import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * Represents a general handler involving file selection
 * 
 * @author Steven Dodson
 * @version 1.0
 */
public class AbstractBaseHandler {
	protected Window window;
	protected FileChooser fileChooser;

	protected AbstractBaseHandler(Window window, FileChooser fileChooser) {
		this.window = window;
		this.fileChooser = fileChooser;
	}
}
