package nonogram.presenters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import nonogram.handlers.OpenHandler;
import nonogram.interfaces.Openable;
import nonogram.models.CellState;
import nonogram.models.NonogramModel;
import nonogram.views.NonogramView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Combines the NonogramView with the Nonogram Model to create a functioning GUI
 * 
 * @author Steven Dodson
 * @version 1.0
 */
public class NonogramPresenter implements Openable {
	private NonogramView view;
	private NonogramModel model;
	private int cellLength;
	private static final String DEFAULT_PUZZLE = "puzzles/space-invader.txt";

	/**
	 * Constructs a new NonogramPresenter
	 * 
	 * @param cellLength the size of one cell on the grid
	 * @throws IOException if the Default puzzle could not be found
	 */
	public NonogramPresenter(int cellLength) throws IOException {
		model = new NonogramModel(DEFAULT_PUZZLE);
		view = new NonogramView();
		this.cellLength = cellLength;
		initializePresenter();
	}

	private void initializePresenter() {
		initializeView();
		bindCellViews();
		syncronize();
		configureButtons();
	}

	private void initializeView() {
		view.initialize(model.getRowClues(), model.getColClues(), cellLength);
		if (getWindow() != null) {
			getWindow().sizeToScene();
		}
	}

	private void bindCellViews() {
		for (int rowIdx = 0; rowIdx < model.getNumRows(); ++rowIdx) {
			for (int colIdx = 0; colIdx < model.getNumCols(); ++colIdx) {
				final int tmpRow = rowIdx;
				final int tmpCol = colIdx;
				view.getCellView(rowIdx, colIdx).setOnMouseClicked(event -> {
					if (event.getButton().equals(MouseButton.PRIMARY)) {
						handleLeftClick(tmpRow, tmpCol);
					} else if (event.getButton().equals(MouseButton.SECONDARY)) {
						handleRightClick(tmpRow, tmpCol);
					}
				});
			}

		}
	}

	private void handleLeftClick(int rowIdx, int colIdx) {
		if (model.getCellState(rowIdx, colIdx).equals(CellState.FILLED)) {
			updateCellState(rowIdx, colIdx, CellState.EMPTY);
		} else {
			updateCellState(rowIdx, colIdx, CellState.FILLED);
		}
	}

	private void handleRightClick(int rowIdx, int colIdx) {
		if (model.getCellState(rowIdx, colIdx).equals(CellState.MARKED)) {
			updateCellState(rowIdx, colIdx, CellState.EMPTY);
		} else {
			updateCellState(rowIdx, colIdx, CellState.MARKED);
		}
	}

	private void updateCellState(int rowIdx, int colIdx, CellState state) {
		if (model.setCellState(rowIdx, colIdx, state)) {
			view.setCellState(rowIdx, colIdx, state);
			view.setRowClueState(rowIdx, model.isSolved());
			view.setColClueState(colIdx, model.isSolved());
			syncronize();
		}
	}

	private void syncronize() {
		for (int rowIdx = 0; rowIdx < model.getNumRows(); ++rowIdx) {
			for (int colIdx = 0; colIdx < model.getNumCols(); ++colIdx) {
				view.setCellState(rowIdx, colIdx, model.getCellState(rowIdx, colIdx));
				view.setColClueState(colIdx, model.isColSolved(colIdx));
			}
			view.setRowClueState(rowIdx, model.isRowSolved(rowIdx));
		}
		view.setPuzzleState(model.isSolved());
		if (model.isSolved()) {
			processVictory();
		}
	}

	private void processVictory() {
		removeCellViewMarks();
		view.showVictoryAlert();
	}

	private void removeCellViewMarks() {
		for (int rowIdx = 0; rowIdx < model.getNumRows(); ++rowIdx) {
			for (int colIdx = 0; colIdx < model.getNumCols(); ++colIdx) {
				if (model.getCellState(rowIdx, colIdx).equals(CellState.MARKED)) {
					view.setCellState(rowIdx, colIdx, CellState.EMPTY);
				}
			}
		}
	}

	private void configureButtons() {
		FileChooser openFileChooser = new FileChooser();
		openFileChooser.setTitle("Open");
		openFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		openFileChooser.setInitialDirectory(new File("."));
		view.getLoadButton().setOnAction(new OpenHandler(getWindow(), openFileChooser, this));

		view.getResetButton().setOnAction(event -> resetPuzzle());
	}

	private void resetPuzzle() {
		model.resetCells();
		syncronize();
	}

	/**
	 * Gets the Pane from the NonogramView
	 * 
	 * @return the Pane from the NonogramView
	 */
	public Pane getPane() {
		return view;
	}

	/**
	 * Gets the Window from the NonogramView's scene
	 * 
	 * @return the Window from the NonogramView's scene, or null if it throws an
	 *         exception
	 */
	public Window getWindow() {
		try {
			return view.getScene().getWindow();
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * Opens the given file and initializes the Presenter
	 * 
	 * @param file the desired file
	 * @throws FileNotFoundException if the given file could not be found
	 */
	@Override
	public void open(File file) throws FileNotFoundException {
		try {
			model = new NonogramModel(file);
			initializePresenter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
