package kr.tmsoft.gptchat.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kr.tmsoft.gptchat.data.room.entity.ChatRoom

@Dao
interface ChatRoomDao {
    @Query("SELECT * FROM chat_room ORDER BY last_update DESC")
    fun getAll(): Flow<List<ChatRoom>>

    @Query("SELECT COUNT(*) FROM chat_room")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chatRoom: ChatRoom)

    @Delete
    fun delete(chatRoom: ChatRoom)
}