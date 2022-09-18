package nonogram.handlers;

import java.io.File;
import java.io.IOException;
import nonogram.interfaces.Openable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * Represents a handler for opening a file.
 * 
 * @author Steven Dodson
 * @version 1.0
 */
public class OpenHandler extends AbstractBaseHandler implements EventHandler<ActionEvent> {
	private Openable opener;

	/**
	 * Constructs an OpenHandler object
	 * 
	 * @param window      the desired window
	 * @param fileChooser a fileChooser
	 * @param opener      an openable object
	 */
	public OpenHandler(Window window, FileChooser fileChooser, Openable opener) {
		super(window, fileChooser);
		this.opener = opener;
	}

	/**
	 * Handles opening a file.
	 */
	@Override
	public void handle(ActionEvent event) {

		File file = fileChooser.showOpenDialog(window);

		if (file != null) {
			try {
				opener.open(file);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}