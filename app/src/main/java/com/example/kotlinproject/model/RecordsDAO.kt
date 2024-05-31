package com.example.kotlinproject.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecordsDAO {
    @Query("SELECT * FROM RECORDS")
    fun getAll(): List<Rec>

    @Query("SELECT * FROM RECORDS where id = :noteId LIMIT 1") // LIMIT 1 para obtener solo primer resultado
    fun getById(noteId:Int): Rec

    @Query("SELECT * FROM RECORDS WHERE id IN (:notesIds) LIMIT :notesQty")
    fun getNotesByIds(notesIds: IntArray, notesQty: Int): List<Rec>

    @Query("SELECT * FROM RECORDS WHERE title LIKE :title AND" +
            ":text LIKE :text LIMIT 1")
    fun findByText(title: String, text: String): Rec

    @Query("DELETE FROM RECORDS")
    fun deleteAll()

    @Insert // Esta anotacion permite que no tengamos que escribir la instruccion INSERT ... INTO ...
    // vararg: cantidad variable de args. 1 o 1,2,3..,4
    fun insert(vararg note: Rec)

    @Delete
    fun delete(note: Rec)

    @Update
    fun update(note: Rec)
}