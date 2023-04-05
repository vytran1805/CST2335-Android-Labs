package algonquin.cst2335.tran0472;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.tran0472.databinding.TemperatureBinding;

public class TemperatureFragment extends Fragment {
    MainActivity mainActivity;

    /**
     * This constructor takes MainActivity object that it will use as a data source for the TextViews
     * @param mainActivity main activity
     */
    public TemperatureFragment(MainActivity mainActivity){
      this.mainActivity = mainActivity;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        TemperatureBinding binding = TemperatureBinding.inflate(inflater);

        binding.tempTextView.setText((int) mainActivity.current);
        binding.tempTextView.setVisibility(View.VISIBLE);
        binding.minTextView.setText((int) mainActivity.minTemp);
        binding.minTextView.setText((int) mainActivity.maxTemp);
        return binding.getRoot();
    }
}
