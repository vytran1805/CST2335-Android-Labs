package algonquin.cst2335.tran0472;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.tran0472.data.ChatRoomViewModel;
import algonquin.cst2335.tran0472.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.tran0472.databinding.ReceiveMessageBinding;
import algonquin.cst2335.tran0472.databinding.SentMessageBinding;


public class ChatRoom extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
    private RecyclerView.Adapter myAdapter;
    ChatRoomViewModel chatModel;
    private ArrayList<ChatMessage> messageList;

    SimpleDateFormat sdf = new SimpleDateFormat("EEEE,dd-MMM-yyyy hh-mm-ss a");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        initialize the chatModel
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messageList = chatModel.messages.getValue();
        if(messageList == null)
        {
            chatModel.messages.postValue( messageList = new ArrayList<ChatMessage>());
        }

//      Add event listener
        binding.sendButton.setOnClickListener(click->{
            String message = binding.textInput.getText().toString();
            String currentDateTime = sdf.format(new Date());
            ChatMessage txt = new ChatMessage(message,currentDateTime,true);
                    //binding.textInput.getText().toString();
            messageList.add(txt);
                    //add(txt.getMessage());
            // notify the Adapter obj that something has been inserted or deleted so the RecyclerView update the new element
            myAdapter.notifyItemInserted(messageList.size()-1);    //the row that needs to be updated is messages.size()-1

            //Clear the previous Text
            binding.textInput.setText("");
        });
        binding.receiveButton.setOnClickListener(click->{

            String message = binding.textInput.getText().toString();
            String currentDateTime = sdf.format(new Date());
            ChatMessage obj = new ChatMessage(message,currentDateTime,false);
            //binding.textInput.getText().toString();
            messageList.add(obj);
            //add(txt.getMessage());
            // notify the Adapter obj that something has been inserted or deleted so the RecyclerView update the new element
            myAdapter.notifyItemInserted(messageList.size()-1);    //the row that needs to be updated is messages.size()-1

            //Clear the previous Text
            binding.textInput.setText("");
        });
//      initialize the myAdapter variable
        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override   //which XML layout for this row? sent_message.xml, comes from
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {    //viewType is what return from getItemViewType() function
                if (viewType == 0) {    //if getItemViewType() returns even number, get sent_message.xml, else get receive_message.xml
                    //always inflates sent_message.xml
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);

                    View root = binding.getRoot();
                    return new MyRowHolder(root);   //get the root
                } else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater(),parent,false);
                    View root = binding.getRoot();
                    return new MyRowHolder(root);
                }

            }

            /**
             * this sets the data for your ViewHolder obj (MyRowHolder class has 2 TextView objects) that will go at row position in the ArrayList
             * @param holder   The ViewHolder which should be updated to represent the contents of the
             *                 item at the given position in the data set.
             * @param position The position of the item within the adapter's data set.
             */
            @Override   //what are the textViews set to?
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage messageOnThisRow = messageList.get(position);     //which string goes in this row?
                holder.messageText.setText(messageOnThisRow.getMessage());

                String currentDateTime = sdf.format(new Date());
                holder.timeText.setText(currentDateTime);
            }

            /**
             * this returns the number of rows in the list
             * @return number of rows
             */
            @Override      //how big is this list
            public int getItemCount() {
                return messageList.size();
            }

            /**
             * which layout to load at row position
             * This function returns an int which is the parameter which gets passed into the onCreateViewHolder() function
             * use when you want to load different layouts for different rows
             * @return an int
             */
            public int getItemViewType(int position){
                //return an int number
                ChatMessage obj = messageList.get(position);
                return obj.isSentButton()?0:1;
            }
        });
//        specify a single column scrolling in a Vertical direction (we can either scroll in a Vertical or Horizontal direction through the items)
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }
    class MyRowHolder extends RecyclerView.ViewHolder{
        protected TextView messageText;
        protected TextView timeText;

        public MyRowHolder(@NonNull View itemView){     //iemView will be the root of the layout: ConstrainLayout in xml file
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }
    public class ChatMessage{
        String message;
        String timeSent;
        boolean isSentButton;
        ChatMessage(String m, String t, boolean sent){
            message = m;
            timeSent = t;
            isSentButton = sent;
        }

        public String getMessage() {
            return message;
        }

        public String getTimeSent() {
            return timeSent;
        }

        public boolean isSentButton() {
            return isSentButton;
        }
    }
}