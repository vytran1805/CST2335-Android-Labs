/**
 * Lab 7
 */
package algonquin.cst2335.tran0472.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
// This tells Room that this Database class in meant for storing ChatMessage obj,
// and uses the ChatMessageDAO class for querying data
@Database(entities = {ChatRoom.ChatMessage.class}, version=1)

/**
 * This abstract class contains the Chat message database,
 * contains only one abstract method to return the DAO for interacting with this database
 */
public abstract class MessageDatabase extends RoomDatabase {
    /**
     * This method/function returns the DAO for interacting with the database
     */
    public abstract ChatMessageDAO cmDAO();
}
