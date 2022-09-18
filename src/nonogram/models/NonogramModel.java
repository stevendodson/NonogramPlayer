package nonogram.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class encapsulates the state and rules of the Nonogram puzzle game.
 * 
 * @author Steven Dodson
 * @version 1.0
 */
public class NonogramModel {

	private static final String DELIMITER = " ";
	private static final int IDX_NUM_ROWS = 0;
	private static final int IDX_NUM_COLS = 1;

	private int[][] rowClues;
	private int[][] colClues;
	private CellState[][] cellStates;

	/**
	 * Construct a new NonogramModel with the given row clues and column clues
	 * 
	 * @param rowClues the row clues
	 * @param colClues the column clues
	 */
	public NonogramModel(int[][] rowClues, int[][] colClues) {
		this.rowClues = deepCopy(rowClues);
		this.colClues = deepCopy(colClues);

		cellStates = initCellStates(rowClues.length, colClues.length);
	}

	/**
	 * Constructs a new NonogramModel from the given file.
	 * 
	 * @param file the file to be parsed
	 * @throws IOException if the file cannot be opened
	 */
	public NonogramModel(File file) throws IOException {
		// Number of rows and columns
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String header = reader.readLine();
		String[] fields = header.split(DELIMITER);
		int numRows = Integer.parseInt(fields[IDX_NUM_ROWS]);
		int numCols = Integer.parseInt(fields[IDX_NUM_COLS]);

		cellStates = initCellStates(numRows, numCols);

		rowClues = readClueLines(reader, numRows);

		colClues = readClueLines(reader, numCols);
		// Close reader
		reader.close();
	}

	/**
	 * Constructs a new NonogramModel from a file with the given filename.
	 * 
	 * @param filename the name of the file to be parsed
	 * @throws IOException if the file cannot be opened
	 */
	public NonogramModel(String filename) throws IOException {
		this(new File(filename));
	}

	/**
	 * Gets the number of columns.
	 * 
	 * @return the number of columns
	 */
	public int getNumCols() {
		return cellStates[0].length;
	}

	/**
	 * Gets the number of rows.
	 * 
	 * @return the number of rows
	 */
	public int getNumRows() {
		return cellStates.length;
	}

	/**
	 * Gets the CellState at the given index.
	 * 
	 * @param rowIdx the row index
	 * @param colIdx the column index
	 * @return the CellState at the given index
	 */
	public CellState getCellState(int rowIdx, int colIdx) {
		return cellStates[rowIdx][colIdx];
	}

	/**
	 * Gets the boolean representation of the CellState at the given index.
	 * 
	 * @param rowIdx the row index
	 * @param colIdx the column index
	 * @return the boolean representation of the CellState at the given index
	 */
	public boolean getCellStateAsBoolean(int rowIdx, int colIdx) {
		return CellState.toBoolean(cellStates[rowIdx][colIdx]);
	}

	private boolean[] getRowAsBoolean(int rowIdx) {
		boolean[] row = new boolean[getNumCols()];
		for (int i = 0; i < row.length; ++i) {
			row[i] = getCellStateAsBoolean(rowIdx, i);
		}
		return row;
	}

	private boolean[] getColAsBoolean(int colIdx) {
		boolean[] col = new boolean[getNumRows()];
		for (int i = 0; i < col.length; ++i) {
			col[i] = getCellStateAsBoolean(i, colIdx);
		}
		return col;
	}

	/**
	 * Sets the CellState at the given index.
	 * 
	 * @param rowIdx the row index
	 * @param colIdx the column index
	 * @param state  the desired state
	 * @return true if the CellState was changed, and false otherwise
	 */
	public boolean setCellState(int rowIdx, int colIdx, CellState state) {
		if (state == null || isSolved() || cellStates[rowIdx][colIdx] == state) {
			return false;
		}
		cellStates[rowIdx][colIdx] = state;
		return true;
	}

	/**
	 * Gets the row clues.
	 * 
	 * @return a 2D array representing the row clues
	 */
	public int[][] getRowClues() {
		return deepCopy(rowClues);
	}

	/**
	 * Gets the column clues.
	 * 
	 * @return a 2D array representing the column clues
	 */
	public int[][] getColClues() {
		return deepCopy(colClues);
	}

	/**
	 * Gets the clue at the given row index.
	 * 
	 * @param rowIdx the row index
	 * @return an array representing the clue at the given row index
	 */
	public int[] getRowClue(int rowIdx) {
		return getRowClues()[rowIdx];
	}

	/**
	 * Gets the clue at the given column index.
	 * 
	 * @param colIdx the column index
	 * @return an array representing the clue at the given column index
	 */
	public int[] getColClue(int colIdx) {
		return getColClues()[colIdx];
	}

	/**
	 * Returns true if the row with the given index is solved.
	 * 
	 * @param rowIdx the row index
	 * @return true if the row with the given index is solved, and false otherwise
	 */
	public boolean isRowSolved(int rowIdx) {
		return Arrays.equals(projectRow(rowIdx), getRowClue(rowIdx));
	}

	/**
	 * Returns true if the column with the given index is solved.
	 * 
	 * @param colIdx the column index
	 * @return true if the column with the given index is solved, and false
	 *         otherwise
	 */
	public boolean isColSolved(int colIdx) {
		return Arrays.equals(projectCol(colIdx), getColClue(colIdx));
	}

	/**
	 * Returns true if the puzzle is solved.
	 * 
	 * @return true if the puzzle is solved, and false otherwise
	 */
	public boolean isSolved() {
		for (int i = 0; i < getNumRows(); ++i) {
			if (!isRowSolved(i)) {
				return false;
			}
		}
		for (int i = 0; i < getNumCols(); ++i) {
			if (!isColSolved(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Changes the state of all the cells to empty
	 */
	public void resetCells() {
		// Set each element of the array to empty
		for (int rowIdx = 0; rowIdx < getNumRows(); ++rowIdx) {
			for (int colIdx = 0; colIdx < getNumCols(); ++colIdx) {
				cellStates[rowIdx][colIdx] = CellState.EMPTY;
			}
		}
	}

	/**
	 * Returns the projection of the cells.
	 * 
	 * @param cells an array representing the state of the cells
	 * @return an array representing the projection of the cells
	 */
	public static int[] project(boolean[] cells) {
		List<Integer> temp = new ArrayList<Integer>();

		int streak = 0;
		for (int i = 0; i < cells.length; ++i) {
			if (cells[i] == true) {
				++streak;
			}
			if (cells[i] == false && streak != 0) {
				temp.add(streak);
				streak = 0;
			}
		}
		if (streak != 0) {
			temp.add(streak);
		}
		if (temp.size() == 0) {
			temp.add(0);
		}

		int[] result = new int[temp.size()];
		for (int i = 0; i < temp.size(); ++i) {
			result[i] = temp.get(i);
		}
		return result;
	}

	/**
	 * Returns the projection of the row with the given index.
	 * 
	 * @param rowIdx the row index
	 * @return an array representing the projection of the row with the given index
	 */
	public int[] projectRow(int rowIdx) {
		return project(getRowAsBoolean(rowIdx));
	}

	/**
	 * Returns the projection of the row with the given index.
	 * 
	 * @param colIdx the column index
	 * @return an array representing the projection of the column with the given
	 *         index
	 */
	public int[] projectCol(int colIdx) {
		return project(getColAsBoolean(colIdx));
	}

	/* Helper methods */

	// This is implemented for you
	private static CellState[][] initCellStates(int numRows, int numCols) {
		// Create a 2D array to store numRows * numCols elements
		CellState[][] cellStates = new CellState[numRows][numCols];

		// Set each element of the array to empty
		for (int rowIdx = 0; rowIdx < numRows; ++rowIdx) {
			for (int colIdx = 0; colIdx < numCols; ++colIdx) {
				cellStates[rowIdx][colIdx] = CellState.EMPTY;
			}
		}

		// Return the result
		return cellStates;
	}

	private static int[][] deepCopy(int[][] array) {
		// You can do this in under 10 lines of code. If you ask the internet
		// "how do I do a deep copy of a 2d array in Java," be sure to cite
		// your source.
		// Note that if we used a 1-dimensional array to store our arrays,
		// we could simply use Arrays.copyOf directly without this helper
		// method.
		// Do not ask about this on Discord. You can do this on your own. :)
		if (array == null) {
			return null;
		}
		int[][] result = new int[array.length][];
		for (int i = 0; i < array.length; ++i) {
			result[i] = Arrays.copyOf(array[i], array[i].length);
		}
		return result;
	}

	// This method is implemented for you. You need to figure out how it is useful.
	private static int[][] readClueLines(BufferedReader reader, int numLines) throws IOException {
		// Create a new 2D array to store the clues
		int[][] clueLines = new int[numLines][];

		// Read in clues line-by-line and add them to the array
		for (int lineNum = 0; lineNum < numLines; ++lineNum) {
			// Read in a line
			String line = reader.readLine();

			// Split the line according to the delimiter character
			String[] tokens = line.split(DELIMITER);

			// Create new int array to store the clues in
			int[] clues = new int[tokens.length];
			for (int idx = 0; idx < tokens.length; ++idx) {
				clues[idx] = Integer.parseInt(tokens[idx]);
			}

			// Store the processed clues in the resulting 2D array
			clueLines[lineNum] = clues;
		}

		// Return the result
		return clueLines;
	}

}
