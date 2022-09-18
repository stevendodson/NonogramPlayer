package nonogram.interfaces;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * An interface to specify that a class has a method to open a file.
 * 
 * @author Steven Dodson
 * @version 1.0
 */
public interface Openable {
	/**
	 * Opens the given file.
	 * 
	 * @param file the file to be opened
	 * @throws FileNotFoundException if the file is not found
	 */
	void open(File file) throws FileNotFoundException;
}
