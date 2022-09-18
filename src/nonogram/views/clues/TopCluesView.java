package nonogram.views.clues;

import javafx.geometry.Orientation;

/**
 * Represents a view containing all column clues displayed above the grid.
 * 
 * @author Steven Dodson
 * @version 1.0
 */
public class TopCluesView extends AbstractGroupCluesView {

	/**
	 * The style class for this view.
	 */
	private static final String STYLE_CLASS = "top-clues-view";

	/**
	 * Constructs a LeftCluesView given a set of clues, cell length, and height.
	 * 
	 * @param colClues   a set of column clues
	 * @param cellLength the length of a cell
	 * @param height     the maximum number of numbered clues among all columns
	 */
	public TopCluesView(int[][] colClues, int cellLength, int height) {
		super(Orientation.HORIZONTAL, STYLE_CLASS, colClues, cellLength, height);
		setMaxWidth(colClues.length * cellLength);

		setPrefWrapLength(Double.MAX_VALUE);
	}

	@Override
	protected AbstractOrientedClueView makeClue(int[] clue, int cellLength, int numClueUnits) {
		return new VerticalClueView(clue, cellLength, numClueUnits);
	}
}
