package com.example.kotlinproject.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BalancesDAO {
    @Query("SELECT * FROM BALANCES")
    fun getAll(): List<Balance>

    @Query("SELECT * FROM BALANCES where id = :balanceId LIMIT 1")
    fun getById(balanceId:Int): Balance

    @Query("SELECT * FROM BALANCES WHERE id IN (:balancesIds) LIMIT :balancesQtty")
    fun getNotesByIds(balancesIds: IntArray, balancesQtty: Int): List<Balance>

    @Query("SELECT * FROM BALANCES WHERE balance_type LIKE :type")
    fun findByText(type: String): Balance

    @Query("DELETE FROM BALANCES")
    fun deleteAll()

    @Insert
    fun insert(vararg balance: Balance)

    @Delete
    fun delete(balance: Balance)

    @Update
    fun update(balance: Balance)
}