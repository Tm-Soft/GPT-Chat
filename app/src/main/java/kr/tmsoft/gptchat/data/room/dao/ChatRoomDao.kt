package kr.tmsoft.gptchat.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kr.tmsoft.gptchat.data.room.entity.ChatRoom

@Dao
interface ChatRoomDao {
    @Query("SELECT * FROM chat_room ORDER BY last_update DESC")
    fun getAll(): Flow<List<ChatRoom>>

    @Query("SELECT COUNT(*) FROM chat_room")
    fun getCount(): Int

    @Query("UPDATE chat_room SET last_update = :date WHERE chatRoomSrl = :chatRoomSrl")
    fun updateLastDate(chatRoomSrl: Long, date: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chatRoom: ChatRoom): Long

    @Delete
    fun delete(chatRoom: ChatRoom)
}