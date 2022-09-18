package nonogram.views.clues;

import javafx.geometry.Orientation;

/**
 * Represents a Vertical column of clues.
 * 
 * @author Steven Dodson
 * @version 1.0
 *
 */
public class VerticalClueView extends AbstractOrientedClueView {

	/**
	 * The style class for this view.
	 */
	private static final String STYLE_CLASS = "vertical-clue-view";

	/**
	 * Constructs a VerticalClueView using the given parameter values.
	 * 
	 * @param colClue    an array of column clues
	 * @param cellLength the length of a cell
	 * @param height     the maximum number of numbered clues among all columns
	 */
	public VerticalClueView(int[] colClue, int cellLength, int height) {
		super(Orientation.VERTICAL, STYLE_CLASS, colClue, cellLength, height);
		setMaxHeight(height * cellLength);
	}

}
