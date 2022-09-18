package nonogram;

import nonogram.presenters.*;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Driver class for the Nonogram puzzle game
 * @author Steven Dodson
 * @version 1.0
 */
public class Main extends Application {
	private static final int IDX_CELL_SIZE = 0;
	private static final int DEFAULT_CELL_SIZE = 30;
	
	/**
	 * Launches the Nonogram puzzle application
	 * @param args the command-line arguments
	 */
	public static void main (String[] args) {
		launch(args);
	}
	
	/**
	 * Starts the JavaJX window
	 * 
	 * @param primaryStage the primary stage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		int cellSize;
		List<String> parameters = getParameters().getUnnamed();
		if (parameters.isEmpty()) {
			cellSize = DEFAULT_CELL_SIZE;
		}
		else {
			cellSize = Integer.parseInt(parameters.get(IDX_CELL_SIZE));
		}
		NonogramPresenter presenter = new NonogramPresenter(cellSize);
		
		Scene scene = new Scene(presenter.getPane());
		scene.getStylesheets().add("style.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Nonogram++");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}