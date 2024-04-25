package edu.jsu.mcis.cs408.crosswordmagic.model;

import android.content.Context;

import edu.jsu.mcis.cs408.crosswordmagic.R;
import edu.jsu.mcis.cs408.crosswordmagic.controller.CrosswordMagicController;
import edu.jsu.mcis.cs408.crosswordmagic.model.dao.DAOFactory;
import edu.jsu.mcis.cs408.crosswordmagic.model.dao.GuessDAO;
import edu.jsu.mcis.cs408.crosswordmagic.model.dao.PuzzleDAO;

public class CrosswordMagicModel extends AbstractModel {
    public static final String TAG = "Model";

    private final int DEFAULT_PUZZLE_ID = 1;

    private final Puzzle puzzle;
    private final DAOFactory daoFactory;
    private String guess;
    private int box_selected;

    public CrosswordMagicModel(Context context) {

        this.daoFactory = new DAOFactory(context);
        PuzzleDAO puzzleDAO = daoFactory.getPuzzleDAO();

        this.puzzle = puzzleDAO.find(DEFAULT_PUZZLE_ID);

    }

    public CrosswordMagicModel(Context context, int puzzleid) {

        this.daoFactory = new DAOFactory(context);
        PuzzleDAO puzzleDAO = daoFactory.getPuzzleDAO();

        this.puzzle = puzzleDAO.find(puzzleid);

    }

    public void getTestProperty() {

        String wordCount = (this.puzzle != null ? String.valueOf(puzzle.getSize()) : "none");
        firePropertyChange(CrosswordMagicController.TEST_PROPERTY, null, wordCount);

    }

    public void getGridLetters() {

        firePropertyChange(CrosswordMagicController.GRID_LETTERS_PROPERTY, null, puzzle.getLetters());
    }

    public void getGridDimensions() {

        Integer[] dimension = new Integer[2];
        dimension[0] = puzzle.getHeight();
        dimension[1] = puzzle.getWidth();
        firePropertyChange(CrosswordMagicController.GRID_DIMENSION_PROPERTY, null, dimension);

    }

    public void getGridNumbers() {
        firePropertyChange(CrosswordMagicController.GRID_NUMBERS_PROPERTY, null, puzzle.getNumbers());
    }

    public void getCluesAcross() {
        firePropertyChange(CrosswordMagicController.CLUES_ACROSS_PROPERTY, null, puzzle.getCluesAcross());
    }

    public void getCluesDown() {
        firePropertyChange(CrosswordMagicController.CLUES_DOWN_PROPERTY, null, puzzle.getCluesDown());
    }

    public void setGuesses(String guess) {
        String[] params = guess.split(" ");
        box_selected = Integer.parseInt(params[0]);
        guess = params[1];

        WordDirection result = puzzle.checkGuess(box_selected, guess);

        if (result != null) {

            String key = box_selected + result.toString();
            Word word = puzzle.getWord(key);
            int wordid = word.getId();
            int puzzleid = word.getPuzzleid();

            GuessDAO puzzleDao = daoFactory.getGuessDAO();
            puzzleDao.create(puzzleid, wordid);
            firePropertyChange(CrosswordMagicController.GUESS_PROPERTY, null, R.string.guess_correct);

        } else {
            firePropertyChange(CrosswordMagicController.GUESS_PROPERTY, null, R.string.guess_incorrect);
        }
    }

    public void getSolved() {
        firePropertyChange(CrosswordMagicController.SOLVED_PROPERTY, null, puzzle.isSolved());
    }

    public void setSelectedBox(Integer box_selected) {
        int oldValue = this.box_selected;
        this.box_selected = box_selected;
        firePropertyChange(CrosswordMagicController.SELECTED_BOX_PROPERTY, oldValue, box_selected);

    }

    public void setUserInput(String guess) {
        String oldValue = this.guess;
        this.guess = guess;
        firePropertyChange(CrosswordMagicController.USER_INPUT_PROPERTY, oldValue, guess);

    }

    public void getPuzzleList() {
        PuzzleDAO puzzleDAO = daoFactory.getPuzzleDAO();
        PuzzleListItem[] puzzleListItems = puzzleDAO.list();
        firePropertyChange(CrosswordMagicController.PUZZLE_LIST_PROPERTY, null, puzzleListItems);
    }

}