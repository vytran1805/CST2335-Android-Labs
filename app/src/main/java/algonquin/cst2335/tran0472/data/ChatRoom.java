package algonquin.cst2335.tran0472.data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.tran0472.R;
import algonquin.cst2335.tran0472.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.tran0472.databinding.ReceiveMessageBinding;
import algonquin.cst2335.tran0472.databinding.SentMessageBinding;


public class ChatRoom extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
    private RecyclerView.Adapter myAdapter;
    ChatRoomViewModel chatModel;
    private ArrayList<ChatMessage> messageList;
    ChatMessageDAO mDAO;

    SimpleDateFormat sdf = new SimpleDateFormat("EEEE,dd-MMM-yyyy hh-mm-ss a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /***********************************************************
         * Lab 7
         ************************************************************/
        //get a database
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class,"database-name").build();
        mDAO = db.cmDAO();

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /**********************************************************
         * Lab 9:Toolbar
         **********************************************************/
        setSupportActionBar(binding.toolbar);
        /*
        Lab 7: initialize the chatModel
         */
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messageList = chatModel.messages.getValue();    //survives rotation changes

        if(messageList == null)
        {
            chatModel.messages.postValue( messageList = new ArrayList<>());
            /**
             *  Lab 7: add messages from database to ArrayList
             *  However, we should only set the adapter for recyclerView inside the execute() function,
             *  so that the RecyclerView show the data after the database has loaded it
             */
            Executor thread = Executors.newSingleThreadExecutor();

            thread.execute(()->{    //add everything from the database
                //        first load the old messages
                List<ChatMessage> previousMessage = mDAO.getAllMessages();  //select * from ChatMessage
                //second thread
                messageList.addAll(previousMessage);
                //main thread                                                   //once we get the data from the database
                runOnUiThread(()-> binding.recycleView.setAdapter(myAdapter));  //then we can load the RecyclerView
            });
        }

//      Add event listener
        binding.sendButton.setOnClickListener(click->{
            String message = binding.textInput.getText().toString();
            String currentDateTime = sdf.format(new Date());
            ChatMessage txt = new ChatMessage(message, currentDateTime, true);
            //binding.textInput.getText().toString();
            messageList.add(txt);
            //no more crashes
            Executor thread = Executors.newSingleThreadExecutor();

            /**********************************************
             //Lab7:insert into database
             *********************************************/
            thread.execute(()->{
                // return the id
                long id = mDAO.insertMessage(txt);
                txt.id = (int) id;   //database is saying what the id is
            });

            // notify the Adapter obj that something has been inserted or deleted so the RecyclerView update the new element
            myAdapter.notifyItemInserted(messageList.size()-1);    //the row that needs to be updated is messages.size()-1

            //Clear the previous Text
            binding.textInput.setText("");
        });
        binding.receiveButton.setOnClickListener(click->{

            String message = binding.textInput.getText().toString();
            String currentDateTime = sdf.format(new Date());
            ChatMessage obj = new ChatMessage(message, currentDateTime, false);
            //binding.textInput.getText().toString();
            messageList.add(obj);
            //add(txt.getMessage());
            //no more crashes
            Executor thread = Executors.newSingleThreadExecutor();

            /**********************************************
             //Lab7:insert into database
             *********************************************/
            thread.execute(()->{
                // return the id
                long id = mDAO.insertMessage(obj);
                obj.id = (int) id;   //database is saying what the id is
            });
            // notify the Adapter obj that something has been inserted or deleted so the RecyclerView update the new element
            myAdapter.notifyItemInserted(messageList.size()-1);    //the row that needs to be updated is messages.size()-1

            //Clear the previous Text
            binding.textInput.setText("");
        });
//      initialize the myAdapter variable, only call this after loading all messages
        myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
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
        };
//        specify a single column scrolling in a Vertical direction (we can either scroll in a Vertical or Horizontal direction through the items)
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        /*
        Lab 8 Fragments: register selectedMessage as a listener to the MutableLiveData object
         */
        chatModel.selectedMessage.observe(this, (newMessageValue) -> {
            MessageDetailsFragment chatFragment = new MessageDetailsFragment(newMessageValue);

            FragmentManager fMgr = getSupportFragmentManager(); //create a FragmentManager object, which is a Singleton object to load a Fragment
            FragmentTransaction tx = fMgr.beginTransaction();   // create what's called a FragmentTransaction, which can add, replace ore remove a fragment
            tx.replace(R.id.fragmentLocation, chatFragment);    //replace() function needs the id of the FrameLayout where it will load the fragment
            tx.commit();    //run the transaction
            tx.addToBackStack("");
        });
    }
    /**
     * This function loads a Menu layout file, it should get a MenuInflater object to load an XML file
     * @param menu Menu object
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    /**
     * This functions loads the corresponding activity once user clicks on menu item
     * @param item menu item
     * @return boolean
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch( item.getItemId() )
        {
            case R.id.item_1:
//put your ChatMessage deletion code here. If you select this item, you shoul show the alert dialog
                //we have

                //                set the alert dialog to ask if user want to delete message
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setMessage("Do you want to delete the message? ");
                builder.setTitle("Question: ");
                // create a button to confirm the action
                builder.setNegativeButton("No",(dialog, cl)->{
                    //if users click No, it does nothing
                });
                builder.setPositiveButton("Yes",(dialog, cl)->{

                    Toast.makeText(this, "One message deleted!", Toast.LENGTH_SHORT).show();
                });
                //this actually shows the functions above
                builder.create().show();    //this is builder pattern: 2 functions called the same time,

            case R.id.item_2:
                Toast.makeText(this, "Version 1.0, created by Vy Tran", Toast.LENGTH_SHORT).show();
//asking if the user wants to delete this message.
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class MyRowHolder extends RecyclerView.ViewHolder{
        TextView messageText;
        TextView timeText;
        public MyRowHolder(@NonNull View itemView) {     //iemView will be the root of the layout: ConstrainLayout in xml file
            super(itemView);
            /***************************************************************************
             //            Lab7: setOnClickListener for itemView so that when we click anywhere on the ConstraintLayout,
             //            it'll load an alert window asking if we want to delete this row
             ***************************************************************************/
            itemView.setOnClickListener(clk -> {    //we have clicked on
//                //we have
//                int position = getAdapterPosition();    //which row was clicked
//                ChatMessage removedMessage = messageList.get(position);
//                //thread1.execute(()->{
//                    //                set the alert dialog to ask if user want to delete message
//                    AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
//                    builder.setMessage("Do you want to delete the message: "+messageText.getText());
//                    builder.setTitle("Question: ");
//                    // create a button to confirm the action
//                    builder.setNegativeButton("No",(dialog, cl)->{
//                        //if user click No, it does nothing
//                    });
//                    builder.setPositiveButton("Yes",(dialog, cl)->{
//                        Executor thread1 = Executors.newSingleThreadExecutor();
//                        //use thread.execute() to avoid crashing whenever using DAO
//                        thread1.execute(()->{   //add everything from the database
//                            mDAO.deleteMessage(removedMessage);  //delete message from the database
//                        });
//                        messageList.remove(position);
//                        myAdapter.notifyItemRemoved(position);  //update the RecycleView
//                        /**
//                         * Snackbar similar to Toast, it shows a message for a LENGTH_SHORT or LENGTH_LONG amount of time
//                         * position is the index of the table, start at 0, so we add 1 to get actual the order of the message
//                         * it also uses the Builder Pattern: .make() returns a Snackbar, .setAction() can undo, .show() shows it
//                         */
//                        Snackbar.make(messageText,"You deleted message #"+(position+1),Snackbar.LENGTH_LONG)
//                                .setAction("Undo",click->{
//                                    messageList.add(position,removedMessage);
//                                    myAdapter.notifyItemInserted(position);
//                                })
//                                .show();
//                     });
//                    //this actually shows the functions above
//                    builder.create().show();    //this is builder pattern: 2 functions called the same time,

                /**************************************************
                 * Lab 8: find the selected chat message and post that value to the selectedMessage variable (created in ChatRoomViewModel
                 **************************************************/
                int position = getAbsoluteAdapterPosition();
                ChatMessage selected = messageList.get(position);
                chatModel.selectedMessage.postValue(selected);

            });
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }

    }

    @Entity //Save obj of the class into the database
    public static class ChatMessage{
        @ColumnInfo(name="Message") //specify the column name
        String message;
        @ColumnInfo(name="TimeSent") //specify the column name
        String timeSent;
        @ColumnInfo(name="SendOrReceive") //specify the column name
        boolean isSent;
        //        Create a primary key
        @PrimaryKey (autoGenerate = true)
        @ColumnInfo(name="id")
        public int id;
        ChatMessage(String message, String timeSent, boolean isSent){
            this.message = message;
            this.timeSent = timeSent;
            this.isSent = isSent;
        }

        public String getMessage() {
            return message;
        }

        public String getTimeSent() {
            return timeSent;
        }

        public boolean isSentButton() {
            return isSent;
        }
    }
}