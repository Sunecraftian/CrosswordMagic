package edu.jsu.mcis.cs408.crosswordmagic.controller;

public class CrosswordMagicController extends AbstractController {
    public static final String TAG = "Controller";

    public static final String TEST_PROPERTY = "TestProperty";
    public static final String GRID_LETTERS_PROPERTY = "GridLetters";
    public static final String GRID_NUMBERS_PROPERTY = "GridNumbers";
    public static final String GRID_DIMENSION_PROPERTY = "GridDimensions";
    public static final String GUESS_PROPERTY = "Guesses";
    public static final String SOLVED_PROPERTY = "Solved";

    public static final String CLUES_ACROSS_PROPERTY = "CluesAcross";
    public static final String CLUES_DOWN_PROPERTY = "CluesDown";
    public static final String SELECTED_BOX_PROPERTY = "SelectedBox";
    public static final String USER_INPUT_PROPERTY = "UserInput";
    public static final String PUZZLE_LIST_PROPERTY = "PuzzleList";

    public void getTestProperty(String value) {
        getModelProperty(TEST_PROPERTY);
    }

    public void getGridDimensions() {
        getModelProperty(GRID_DIMENSION_PROPERTY);
    }

    public void getGridLetters() {
        getModelProperty(GRID_LETTERS_PROPERTY);
    }

    public void getGridNumbers() {
        getModelProperty(GRID_NUMBERS_PROPERTY);
    }

    public void getCluesAcross() {
        getModelProperty(CLUES_ACROSS_PROPERTY);
    }

    public void getCluesDown() {
        getModelProperty(CLUES_DOWN_PROPERTY);
    }

    public void setGuesses(String guess) {
        setModelProperty(GUESS_PROPERTY, guess);
    }

    public void getSolved() {
        getModelProperty(SOLVED_PROPERTY);
    }

    public void setUserInput(String userInput) {
        setModelProperty(USER_INPUT_PROPERTY, userInput);
    }

    public void setBoxSelection(int n) {
        setModelProperty(SELECTED_BOX_PROPERTY, n);
    }

    public void getPuzzleList() {
        getModelProperty(PUZZLE_LIST_PROPERTY);
    }
}