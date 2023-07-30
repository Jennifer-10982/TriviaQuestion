package algonquin.cst2335.triviaquestion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ProjectViewModel extends ViewModel {
    public int display_points;
    public MutableLiveData<ArrayList<PlayerInformation>> ranking = new MutableLiveData<>();

    public MutableLiveData<PlayerInformation> selectedPlayer = new MutableLiveData<>(null);
}
