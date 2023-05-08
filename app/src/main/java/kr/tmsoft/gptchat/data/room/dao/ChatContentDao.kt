package kr.tmsoft.gptchat.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kr.tmsoft.gptchat.data.room.entity.ChatContent
import kr.tmsoft.gptchat.data.room.entity.ChatRoom

@Dao
interface ChatContentDao {
    @Query("SELECT * FROM chat_content")
    fun getAll(): Flow<List<ChatContent>>

    @Query("SELECT * FROM chat_content WHERE status = 'wait' ORDER BY last_update ASC LIMIT 1")
    fun getWaitContent(): Flow<ChatContent>

    @Query("SELECT * FROM chat_content WHERE chat_room_srl = :chatRoomSrl AND role = 'assistant'")
    fun getAssistantContents(chatRoomSrl: Long): List<ChatContent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chatContent: ChatContent)

    @Delete
    fun delete(chatContent: ChatContent)
}