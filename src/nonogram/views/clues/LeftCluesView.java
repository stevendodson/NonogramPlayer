package nonogram.views.clues;

import javafx.geometry.Orientation;

/**
 * Represents a view containing all row clues displayed to the left of the grid.
 * 
 * @author Steven Dodson
 * @version 1.0
 */
public class LeftCluesView extends AbstractGroupCluesView {

	/**
	 * The style class for this view.
	 */
	private static final String STYLE_CLASS = "left-clues-view";

	/**
	 * Constructs a LeftCluesView given a set of clues, cell length, and width.
	 * 
	 * @param rowClues   a set of horizontal row clues
	 * @param cellLength the length of a cell
	 * @param width      the maximum number of numbered clues among all rows
	 */
	public LeftCluesView(int[][] rowClues, int cellLength, int width) {
		super(Orientation.VERTICAL, STYLE_CLASS, rowClues, cellLength, width);
		setMaxHeight(rowClues.length * cellLength);

		setPrefWrapLength(Double.MAX_VALUE);
	}

	@Override
	protected AbstractOrientedClueView makeClue(int[] clue, int cellLength, int numClueUnits) {
		return new HorizontalClueView(clue, cellLength, numClueUnits);
	}
}