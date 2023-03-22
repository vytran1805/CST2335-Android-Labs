package algonquin.cst2335.tran0472.data;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.tran0472.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {
    ChatRoom.ChatMessage selected;

    public MessageDetailsFragment(ChatRoom.ChatMessage m){
        selected = m;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.messageText.setText(selected.message);
        binding.timeText.setText(selected.timeSent);
        binding.databaseText.setText("Id = "+selected.id);
        return binding.getRoot();
    }

}
