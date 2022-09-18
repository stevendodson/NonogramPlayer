package nonogram.views.clues;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;

/**
 * Represents a grouping of clues.
 * 
 * @author Steven Dodson
 * @version 1.0
 */
public abstract class AbstractGroupCluesView extends FlowPane {

	/**
	 * A list of oriented clues.
	 */
	private List<AbstractOrientedClueView> clueViews;

	/**
	 * Initializes an AbstractGroupClues view using the given parameter values.
	 * 
	 * @param orientation  the orientation of the view (HORIZONTAL or VERTICAL)
	 * @param styleClass   the name of a styling class for the view
	 * @param setOfClues   an array of clue arrays
	 * @param cellLength   the length of a cell
	 * @param numClueUnits the maximum number of numbered clues overall
	 */
	protected AbstractGroupCluesView(Orientation orientation, String styleClass, int[][] setOfClues, int cellLength,
			int numClueUnits) {
		// Set the orientation -------------------------------------------------
		setOrientation(orientation);

		// Add styling class ---------------------------------------------------
		getStyleClass().add(styleClass);

		// Create and add clues to list
		clueViews = new ArrayList<>(setOfClues.length);
		for (int[] clue : setOfClues) {
			clueViews.add(makeClue(clue, cellLength, numClueUnits));
		}
		getChildren().addAll(clueViews);
		setMaxWidth(USE_PREF_SIZE);
		setMaxHeight(USE_PREF_SIZE);

		setPrefWrapLength(Double.MAX_VALUE);
	}

	protected abstract AbstractOrientedClueView makeClue(int[] clue, int cellLength, int numClueUnits);

	/**
	 * Updates the state at a given clue index.
	 * 
	 * @param idx    clue index
	 * @param solved solve status
	 */
	public void setState(int idx, boolean solved) {
		clueViews.get(idx).setState(solved);
	}
}
