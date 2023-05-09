package kr.tmsoft.gptchat.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kr.tmsoft.gptchat.data.room.dao.ChatContentDao
import kr.tmsoft.gptchat.data.room.dao.ChatRoomDao
import kr.tmsoft.gptchat.data.room.entity.ChatContent
import kr.tmsoft.gptchat.data.room.entity.ChatRoom

@Database(
    entities = [ChatRoom::class, ChatContent::class],
    version = 2
)
abstract class OpenAiDatabase: RoomDatabase() {
    abstract fun chatRoomDao(): ChatRoomDao

    abstract fun chatContentDao(): ChatContentDao

    companion object {
        private var INSTANCE : OpenAiDatabase? = null

        fun getInstance(context: Context): OpenAiDatabase? {
            if (INSTANCE == null) {
                synchronized(OpenAiDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        OpenAiDatabase::class.java,"openaiChat")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE
        }
    }
}