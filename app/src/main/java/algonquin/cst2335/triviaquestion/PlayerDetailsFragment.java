/*
 * Student Name: Jennifer Huynh
 * Student Number: 041086110
 * Lab Section: CST2335_022
 * Due Date: 07/08/23
 * Description: This class is used to load the fragment onto the application with the following information
 * regarding the specific player that the user selected. On the fragment, the username and
 * their score are displayed.
 * */
package algonquin.cst2335.triviaquestion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.triviaquestion.databinding.SelectionFragBinding;

/**
 * This class is used to load the fragment onto the application with the following information
 * regarding the specific player that the user selected. On the fragment, the username and
 * their score are displayed.
 */
public class PlayerDetailsFragment extends Fragment {
    /**
     * thisPlayer refers to the player that the user has selected. thisPlayer is to contain
     * the information regarding their name and their highscore.
     */
    PlayerInformation thisPlayer;

    /**
     * This is a parameterized constructor with the object toShow of type PlayerInformation. The information
     * for toShow will be use to pass onto the fragment where the details will be shown.
     * @param toShow is an object of type PlayerInformation use to contain information about the selected
     *               player such as their username and highscore.
     */
    public PlayerDetailsFragment(PlayerInformation toShow){
        thisPlayer = toShow;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SelectionFragBinding variableBinding = SelectionFragBinding.inflate(inflater);

        variableBinding.selectedPoints.setText(thisPlayer.counter);
        variableBinding.selectedName.setText(thisPlayer.playerName);

        return variableBinding.getRoot();
    }
}
