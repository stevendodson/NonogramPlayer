package nonogram.views;

import nonogram.models.CellState;
import nonogram.views.clues.*;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * This class is a BorderPane that displays the row clues, column clues, and
 * cells.
 * 
 * @author Steven Dodson
 * @version 1.0
 *
 */
public class NonogramView extends BorderPane {
	private static final String STYLE_CLASS = "nonogram-view";
	private static final String SOLVED_STYLE_CLASS = "nonogram-view-solved";
	private LeftCluesView leftCluesView;
	private TopCluesView topCluesView;
	private CellGridView cellGridView;
	private HBox bottomHBox;
	private Button loadBtn;
	private Button resetBtn;

	/**
	 * Constructs a new NonogramView
	 */
	public NonogramView() {
		getStyleClass().add(STYLE_CLASS);

	}

	/**
	 * Initializes the view.
	 * 
	 * @param rowClues   the row clues
	 * @param colClues   the column clues
	 * @param cellLength the size of a cell
	 */
	public void initialize(int[][] rowClues, int[][] colClues, int cellLength) {
		leftCluesView = new LeftCluesView(rowClues, cellLength, findMaxLength(rowClues));
		topCluesView = new TopCluesView(colClues, cellLength, findMaxLength(colClues));
		cellGridView = new CellGridView(rowClues.length, colClues.length, cellLength);

		setLeft(leftCluesView);
		setTop(topCluesView);
		setAlignment(topCluesView, Pos.CENTER_RIGHT);
		setCenter(cellGridView);
		initBottomHBox();
		setBottom(bottomHBox);
	}

	private int findMaxLength(int[][] clues) {
		int max = -1;
		for (int i = 0; i < clues.length; ++i) {
			if (clues[i].length > max) {
				max = clues[i].length;
			}
		}
		return max;
	}

	private void initBottomHBox() {
		loadBtn = new Button("Load");
		resetBtn = new Button("Reset");
		bottomHBox = new HBox(loadBtn, resetBtn);
		bottomHBox.setAlignment(Pos.CENTER);
	}

	/**
	 * Gets the CellView on the CellGridView with the given indices.
	 * 
	 * @param rowIdx the row index
	 * @param colIdx the column index
	 * @return the CellView on the CellGridView with the given indices
	 */
	public CellView getCellView(int rowIdx, int colIdx) {
		return cellGridView.getCellView(rowIdx, colIdx);
	}

	/**
	 * Sets the state of the cell in the CellGridView with the given indices.
	 * 
	 * @param rowIdx the row index
	 * @param colIdx the column index
	 * @param state  the new state of the cell
	 */
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		cellGridView.setCellState(rowIdx, colIdx, state);
	}

	/**
	 * Updates the state of the HorizontalClueView with the given index.
	 * 
	 * @param rowIdx the row index
	 * @param solved the new state of the clue
	 */
	public void setRowClueState(int rowIdx, boolean solved) {
		leftCluesView.setState(rowIdx, solved);
	}

	/**
	 * Updates the state of the VerticalClueView with the given index.
	 * 
	 * @param colIdx the column index
	 * @param solved the new state of the clue
	 */
	public void setColClueState(int colIdx, boolean solved) {
		topCluesView.setState(colIdx, solved);
	}

	/**
	 * Updates the style class depending on the state of the puzzle.
	 * 
	 * @param solved the state of the puzzle
	 */
	public void setPuzzleState(boolean solved) {
		if (solved) {
			getStyleClass().add(SOLVED_STYLE_CLASS);
		} else {
			getStyleClass().removeAll(SOLVED_STYLE_CLASS);
		}
	}

	/**
	 * Gets the load button
	 * 
	 * @return the load button
	 */
	public Button getLoadButton() {
		return loadBtn;
	}

	/**
	 * Gets the reset button
	 * 
	 * @return the reset button
	 */
	public Button getResetButton() {
		return resetBtn;
	}

	/**
	 * Shows a victory alert.
	 */
	public void showVictoryAlert() {
		Alert victoryAlert = new Alert(AlertType.INFORMATION, "You Win!");
		victoryAlert.setHeaderText("Congratulations!");
		victoryAlert.setTitle("Puzzle Solved");
		victoryAlert.showAndWait();
	}
}
