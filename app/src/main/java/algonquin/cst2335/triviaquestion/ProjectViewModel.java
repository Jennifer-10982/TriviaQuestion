/*
 * Student Name: Jennifer Huynh
 * Student Number: 041086110
 * Lab Section: CST2335_022
 * Due Date: 07/08/23
 * Description: The subclass ProjectViewModel implements ViewModel. The class contains variables that are able to store
 * and react to update/change to their current values.
 * */
package algonquin.cst2335.triviaquestion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/**
 * The subclass ProjectViewModel implements ViewModel. The class contains variables that are able to store
 * and react to update/change to their current values.
 */
public class ProjectViewModel extends ViewModel {
    /**
     * display_points is the total points that the user has accumulated after completing the game.
     * The points can only contain whole numbers
     */
    public int display_points;
    /**
     * Ranking is a list that contains the all the player with their id number that is inserted into the datbase,
     * their highscores, and their username. The
     */
    public MutableLiveData<ArrayList<PlayerInformation>> ranking = new MutableLiveData<>();

    /**
     *  selectedPlayer is contains informatio regarding a certain individual player. It contains only their
     *  id, username, and their highscore.
     */
    public MutableLiveData<PlayerInformation> selectedPlayer = new MutableLiveData<>(null);
}
