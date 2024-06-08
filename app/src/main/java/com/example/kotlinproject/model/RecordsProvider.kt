package com.example.kotlinproject.model

import android.util.Log
import com.example.kotlinproject.App
import com.example.kotlinproject.R

class RecordsProvider {
    companion object {
        private val instance = RecordsProvider() // used to be available only from inside. Singleton.
        fun getProvider(): RecordsProvider {
            return instance
        }
    }
    private var records = mutableListOf<Rec>()
    private var balances = mutableListOf<Balance>()

    private var db:AppDataBase = AppDataBase.getInstance()
    private val listeners = mutableListOf<()->Unit>()

    private var categoriesIds = mutableListOf<String>()
    private var categories = mutableListOf(
        Category("Comida y alimentos", R.drawable.ic_category_food, R.color.sad_grey, ""),
        Category("Restaurant y comida rapida", R.drawable.ic_category_fast_food, R.color.sad_grey, ""),
        Category("Ropa", R.drawable.ic_category_clothes, R.color.sad_grey, ""),
        Category("Vehiculos", R.drawable.ic_category_vehicle, R.color.sad_grey, ""),
        Category("Mantenimiento vehiculos", R.drawable.ic_category_vehicle_maintenance, R.color.sad_grey, ""),
        Category("Recitales y eventos", R.drawable.ic_category_concerts, R.color.sad_grey, ""),
        Category("Salud", R.drawable.ic_category_health, R.color.sad_grey, ""),
        Category("Estudios particulares", R.drawable.ic_category_particular_studies, R.color.sad_grey, ""),
        Category("Medicamentos e insumos", R.drawable.ic_category_medicine, R.color.sad_grey, ""),
        Category("Hobbies", R.drawable.ic_category_hobbies, R.color.sad_grey, ""),
        Category("Pintura, dibujo y fotografia", R.drawable.ic_category_painting_drawing_and_photography, R.color.sad_grey, ""),
        Category("Inversiones y finanzas", R.drawable.ic_category_investment_and_finances, R.color.sad_grey, ""),
        Category("Salario", R.drawable.ic_category_salary, R.color.sad_grey, ""),
        Category("Otros", R.drawable.ic_other_generic, R.color.sad_grey, ""),
    )

    init{
        categories.forEach{ category ->
            categoriesIds.add(category.categoryName)
        }
        records = db.recsDao().getAll().toMutableList()
        balances = db.balancesDao().getAll().toMutableList()
    }

    fun registerListener(listener: ()->Unit){
        listeners.add(listener)
    }

    fun unregisterListener(listener: ()->Unit){// TODO llamar cuando se vuelve para atrás
        listeners.remove(listener)
    }

    fun updateTotalBalance(): Int{
        var iRet: Int = App.RET_FALSE
        val balancesQtty = db.balancesDao().countBalances()
        if( balancesQtty > 0) {
            val obtainedTotalsReg = db.balancesDao().getTotalBalanceRec()
            if(obtainedTotalsReg != null) {
                // Obtain current records and perform total amount and total qtty operations
                var auxTotal: Double = 0.0
                var auxQtty: Int = 0
                val currentRecs = db.recsDao().getAll()
                currentRecs.forEach {
                    auxTotal += it.amount
                    auxQtty++
                }

                // Update total balance record
                Log.d("Debugger", "Nuevo total: $auxTotal")
                obtainedTotalsReg.amount = auxTotal
                obtainedTotalsReg.qtty = auxQtty
                db.balancesDao().updateTotalBalanceRec(obtainedTotalsReg)
                iRet = App.RET_TRUE
            }
        } else {
            // Totals record doesn't exist
            Log.d("Debugger", "No hay registros en la tabla de balances")
            iRet = App.RET_FALSE
        }
        return iRet // TODO manage errors with exceptions
    }

    fun updateCategoryBalance(record : Rec): Int{
        var iRet: Int
        if(db.balancesDao().countBalances() > 0) {
            val obtainedCategBalance = db.balancesDao().getBalanceByCategory(record.category.categoryName)
            val amount = record.amount
            // Check existence of category rec and update category total amount
            if(obtainedCategBalance != null){
                obtainedCategBalance.amount += amount
                obtainedCategBalance.qtty++
                db.balancesDao().updateCategoryBalanceRec(obtainedCategBalance)
            } else {
                // Total balances record doesn't exist. Adds it
                db.balancesDao().insertIntoBalances(Balance(record.category.categoryName, 1, record.amount))
            }

            Log.d("Debugger", "Al total se le suman: $amount")
            iRet = App.RET_TRUE
        } else {
            Log.d("Debugger", "No hay registros en la tabla de balances")
            iRet = App.RET_FALSE
        }
        return iRet // TODO manage errors with exceptions
    }

    // Every time notes are added, also listeners are executed
    fun addRecord(record: Rec){
        records.add(record)
        listeners.forEach{ it.invoke()}
        db.recsDao().insert(record)
        if(db.balancesDao().countBalances() == 0) {
            Log.d("Debugger", "Initialize balances table")
            db.balancesDao().insertIntoBalances(Balance(App.context.getString(R.string.text_db_tag_totals), 0, 0.0))
        }
        updateCategoryBalance(record)
        updateTotalBalance()
    }

    fun getRecordsList():MutableList<Rec>{
        return records
    }

    fun getRecord(i : Int) : Rec? {
        if (i >= 0){
            return records[i]
        } else {
            return null
        }
    }

    fun getCategoriesList():MutableList<Category>{
        return categories
    }

    fun getCategoriesIdsList():MutableList<String>{
        return categoriesIds
    }

    fun getDb():AppDataBase{
        return db
    }
}