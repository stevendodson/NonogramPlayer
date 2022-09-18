package nonogram.models;

/**
 * This enum is used to represent the state of each cell in the NonogramModel
 * 
 * @author Steven Dodson
 * @version 1.0
 */
public enum CellState {
	/**
	 * The cell is empty.
	 */
	EMPTY,

	/**
	 * The cell is filled.
	 */
	FILLED,

	/**
	 * The cell is marked.
	 */
	MARKED;

	/**
	 * Returns the binary representation of the CellState: True if filled, and false
	 * otherwise
	 * 
	 * @param state the CellState
	 * @return true if the CellState is filled, and false otherwise
	 */
	public static boolean toBoolean(CellState state) {
		return state == FILLED ? true : false;
	}
}
