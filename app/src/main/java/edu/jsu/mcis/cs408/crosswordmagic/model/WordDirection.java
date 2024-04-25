package edu.jsu.mcis.cs408.crosswordmagic.model;

public enum WordDirection {

    ACROSS("A"),
    DOWN("D");
    private final String message;

    WordDirection(String msg) {
        message = msg;
    }

    @Override
    public String toString() {
        return message;
    }

}