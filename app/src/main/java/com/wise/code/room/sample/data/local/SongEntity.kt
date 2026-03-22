package com.wise.code.room.sample.data.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey val id: Int,
)

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey val id: Int,
)

// Junction Table / Cross Ref / Many-to-many relationships
@Entity(primaryKeys = ["song_id", "playlist_id"])
data class PlaylistAndSongsCrossRef(
    @ColumnInfo("song_id") val songId: Int,
    @ColumnInfo("playlist_id") val playlistId: Int,
)

// Left Join - Many-to-many: Given a song, fetch all playlists it contains
data class SongWithPlaylists(
    @Embedded val song: SongEntity,
    @Relation(
        parentColumn = "id", // Relation - SongEntity
        entityColumn = "id", // Relation - PlaylistEntity
        associateBy = Junction(
            value = PlaylistAndSongsCrossRef::class,
            parentColumn = "song_id", // Column in CrossRef matching SongEntity.id
            entityColumn = "playlist_id", // Column in CrossRef matching PlaylistEntity.id
        )
    )
    val playlists: List<PlaylistEntity>
)

// Left join - Many-to-many: Given a Playlist, fetch all Songs it contains
data class PlaylistWithSongs(
    @Embedded val playlist: PlaylistEntity,
    @Relation(
        parentColumn = "id", // PlaylistEntity
        entityColumn = "id", // SongEntity
        associateBy = Junction(
            value = PlaylistAndSongsCrossRef::class,
            parentColumn = "playlist_id", // Column in CrossRef matching PlaylistEntity.id
            entityColumn = "song_id" // Column in CrossRef matching SongEntity.id
        )
    )
    val songs: List<SongEntity>
)
