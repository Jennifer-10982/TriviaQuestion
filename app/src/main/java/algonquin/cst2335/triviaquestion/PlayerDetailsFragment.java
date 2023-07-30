package algonquin.cst2335.triviaquestion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.triviaquestion.databinding.SelectionFragBinding;

public class PlayerDetailsFragment extends Fragment {
    PlayerInformation thisPlayer;

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
