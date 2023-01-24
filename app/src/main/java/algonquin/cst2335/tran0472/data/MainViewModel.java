package algonquin.cst2335.tran0472.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
// Lab2: 4/6: The reason for using a ViewModel object separate from the AppCompatActivity is so that
// the variables are in a different object that doesn't get deleted on orientation changes (when rotate the screen).
public class MainViewModel extends ViewModel {
    public MutableLiveData<String> editString = new MutableLiveData<>();
//    Lab2 (5/6): Add Compound buttons
    public MutableLiveData<Boolean> compoundBtn_isSelected =new MutableLiveData<>();

}
