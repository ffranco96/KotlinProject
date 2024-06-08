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

    @Query("SELECT * FROM BALANCES where type = :type LIMIT 1")
    fun getBalanceByType(type: String): Balance

    @Query("SELECT * FROM BALANCES where type LIKE 'Totals' LIMIT 1")
    fun getTotalBalanceRec(): Balance

    @Query("SELECT * FROM BALANCES where type LIKE :categoryName LIMIT 1")
    fun getBalanceByCategory(categoryName: String): Balance

    @Query("SELECT COUNT (*) FROM BALANCES")
    fun countBalances():Int

    //@Query("SELECT * FROM BALANCES WHERE id IN (:balancesCategs) LIMIT :balancesQtty")
    //fun getBalancesByIds(balancesCategs: , qtty: Int): List<Balance>

    @Query("DELETE FROM BALANCES")
    fun deleteAll()

    @Insert
    fun insertIntoBalances(vararg balance: Balance)

    @Delete
    fun deleteOnBalances(balance: Balance)

    @Update // cambiar la primary key y hacer directamente el update. o ver sino otra forma de actualizarel amojunt y el quantity.
    fun updateCategoryBalanceRec(balance: Balance)

    @Update
    fun updateTotalBalanceRec(balance: Balance)
}