package edu.jsu.mcis.cs408.crosswordmagic.view;

import java.beans.PropertyChangeEvent;

public interface AbstractView {

    void modelPropertyChange(final PropertyChangeEvent evt);

}