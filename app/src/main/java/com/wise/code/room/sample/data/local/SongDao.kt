package com.wise.code.room.sample.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface SongDao {
    @Transaction
    @Query("SELECT * FROM songs")
    suspend fun getSongsWithPlaylist(): SongWithPlaylists

    @Transaction
    @Query("SELECT * FROM playlists")
    suspend fun getPlaylistsWithSongs(): PlaylistWithSongs
}