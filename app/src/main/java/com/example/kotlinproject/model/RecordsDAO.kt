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

    @Query("SELECT * FROM RECORDS where id = :recId LIMIT 1") // LIMIT 1 para obtener solo primer resultado
    fun getById(recId:Int): Rec

    @Query("SELECT * FROM RECORDS WHERE id IN (:recsIds) LIMIT :recordsQty")
    fun getRecordsByIds(recsIds: IntArray, recordsQty: Int): List<Rec>

    @Query("SELECT * FROM RECORDS WHERE title LIKE :title AND" +
            ":text LIKE :text LIMIT 1")
    fun findByText(title: String, text: String): Rec

    @Query("DELETE FROM RECORDS")
    fun deleteAll()

    @Insert // Esta anotacion permite que no tengamos que escribir la instruccion INSERT ... INTO ...
    // vararg: cantidad variable de args. 1 o 1,2,3..,4
    fun insert(vararg rec: Rec)

    @Delete
    fun delete(rec: Rec)

    @Update
    fun update(rec: Rec)
}