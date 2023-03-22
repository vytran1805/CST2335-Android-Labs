package algonquin.cst2335.tran0472.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/**
 * Store ChatMessages to survive rotation changes
 */
public class ChatRoomViewModel extends ViewModel {
    public MutableLiveData<ArrayList<ChatRoom.ChatMessage>> messages = new MutableLiveData<>();
    /**
     * Lab8: Fragments
     */
    public MutableLiveData<ChatRoom.ChatMessage> selectedMessage = new MutableLiveData< >();

}
