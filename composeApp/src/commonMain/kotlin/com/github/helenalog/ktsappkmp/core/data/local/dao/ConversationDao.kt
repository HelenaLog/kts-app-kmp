package com.github.helenalog.ktsappkmp.core.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.github.helenalog.ktsappkmp.core.data.local.entity.ConversationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationDao {
    @Upsert
    suspend fun upsertAll(conversations: List<ConversationEntity>)

    @Query("SELECT * FROM conversations ORDER BY date_updated DESC")
    fun observeAll(): Flow<List<ConversationEntity>>

    @Query("SELECT * FROM conversations WHERE user_name LIKE '%' || :query || '%' ORDER BY date_updated DESC")
    fun observeByQuery(query: String): Flow<List<ConversationEntity>>

    @Query("DELETE FROM conversations")
    suspend fun deleteAll()

    @Query("SELECT * FROM conversations WHERE user_name LIKE '%' || :query || '%' ORDER BY date_updated DESC")
    suspend fun getByQuery(query: String): List<ConversationEntity>
}