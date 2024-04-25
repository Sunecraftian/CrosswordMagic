package edu.jsu.mcis.cs408.crosswordmagic.view;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.beans.PropertyChangeEvent;

import edu.jsu.mcis.cs408.crosswordmagic.controller.CrosswordMagicController;
import edu.jsu.mcis.cs408.crosswordmagic.databinding.FragmentClueBinding;

public class ClueFragment extends Fragment implements TabFragment, AbstractView {
    private CrosswordMagicController controller;
    private FragmentClueBinding binding;

    private String title;

    public ClueFragment() {
        super();
    }

    public ClueFragment(String title) {
        super();
        this.title = title;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentClueBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        this.controller = ((MainActivity) getContext()).getController();

        controller.addView(this);

        controller.getCluesDown();
        controller.getCluesAcross();
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {

        String propertyName = evt.getPropertyName();

        if (propertyName.equals(CrosswordMagicController.CLUES_ACROSS_PROPERTY)) {

            binding.aContainer.setText(evt.getNewValue().toString());
            binding.aContainer.setMovementMethod(new ScrollingMovementMethod());

        } else if (propertyName.equals(CrosswordMagicController.CLUES_DOWN_PROPERTY)) {

            binding.dContainer.setText(evt.getNewValue().toString());
            binding.dContainer.setMovementMethod(new ScrollingMovementMethod());

        }

    }

    @Override
    public String getTabTitle() {
        return title;
    }
}

