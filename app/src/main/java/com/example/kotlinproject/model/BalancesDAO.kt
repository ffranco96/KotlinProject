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

    @Query("SELECT * FROM BALANCES where id = :balanceType LIMIT 1")
    fun getBalanceByBalanceType(balanceType: String): Balance

    @Query("SELECT * FROM BALANCES where balance_type LIKE 'totals' LIMIT 1")
    fun getTotalBalanceRec(): Balance

    @Query("SELECT * FROM BALANCES where balance_type LIKE :categoryName LIMIT 1")
    fun getCategoryBalanceRec(categoryName: String): Balance

    @Query("SELECT COUNT (*) FROM BALANCES")
    fun countBalances():Int

    @Query("SELECT * FROM BALANCES WHERE id IN (:balancesIds) LIMIT :balancesQtty")
    fun getBalancesByIds(balancesIds: IntArray, balancesQtty: Int): List<Balance>

    @Query("SELECT * FROM BALANCES WHERE balance_type LIKE :type")
    fun findByText(type: String): Balance

    @Query("DELETE FROM BALANCES")
    fun deleteAll()

    @Insert
    fun insertIntoBalances(vararg balance: Balance)

    @Delete
    fun deleteOnBalances(balance: Balance)

    @Query("UPDATE BALANCES SET amount = :amount WHERE balance_type = :category")
    fun updateCategoryBalanceRec(amount: Double, category: String)

    @Update
    fun updateTotalBalanceRec(balance: Balance)
}