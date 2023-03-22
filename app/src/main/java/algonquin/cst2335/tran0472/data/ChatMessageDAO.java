/**
 * Lab 7
 */
package algonquin.cst2335.tran0472.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ChatMessageDAO {
    /**
     * This function inserts a ChatMessage object and then return the newly created ID as a long
     * @param m ChatMessage obj
     * @return a newly created ID
     */
    @Insert
    public long insertMessage(ChatRoom.ChatMessage m);
    @Query("Select * from ChatMessage")
    public List<ChatRoom.ChatMessage> getAllMessages();
    @Delete
    public void deleteMessage(ChatRoom.ChatMessage m);
}
