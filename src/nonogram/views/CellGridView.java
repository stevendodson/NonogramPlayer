package nonogram.views;

import nonogram.models.CellState;
import javafx.scene.layout.GridPane;

/**
 * This class is a GridPane that displays the cell states
 * 
 * @author Steven Dodson
 * @version 1.0
 */
public class CellGridView extends GridPane {
	private static final String STYLE_CLASS = "cell-grid-view";
	private CellView[][] cellViews;

	/**
	 * Constructs a new CellGridView with the given number of rows and columns and
	 * size of the cells.
	 * 
	 * @param numRows    the number of rows
	 * @param numCols    the number of columns
	 * @param cellLength the size of one cell in the grid
	 */
	public CellGridView(int numRows, int numCols, int cellLength) {
		cellViews = new CellView[numRows][numCols];
		for (int i = 0; i < numRows; ++i) {
			for (int j = 0; j < numCols; ++j) {
				cellViews[i][j] = new CellView(cellLength);
				setCellState(i, j, CellState.EMPTY);
				add(cellViews[i][j], j, i);
			}
		}
		getStyleClass().add(STYLE_CLASS);
	}

	/**
	 * Initializes the cells in the grid.
	 * 
	 * @param numRows    the number of rows
	 * @param numCols    the number of columns
	 * @param cellLength the size of one cell
	 */
	public void initCells(int numRows, int numCols, int cellLength) {
		getChildren().clear();

		for (int i = 0; i < numRows; ++i) {
			addRow(i, cellViews[i]);
		}
	}

	/**
	 * Gets the CellView using the given indices.
	 * 
	 * @param rowIdx the row index
	 * @param colIdx the column index
	 * @return the CellView with the given indices
	 */
	public CellView getCellView(int rowIdx, int colIdx) {
		return cellViews[rowIdx][colIdx];
	}

	/**
	 * Sets the state of the cell with the given indices.
	 * 
	 * @param rowIdx the row index
	 * @param colIdx the column index
	 * @param state  the new state of the cell
	 */
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		cellViews[rowIdx][colIdx].setState(state);
	}
}
