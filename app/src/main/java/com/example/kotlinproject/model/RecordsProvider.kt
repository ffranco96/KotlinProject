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
        Category("Comida y alimentos", R.drawable.ic_category_food, R.color.categ_color_food, ""),
        Category("Restaurant y comida rapida", R.drawable.ic_category_fast_food, R.color.categ_color_fast_food, ""),
        Category("Ropa", R.drawable.ic_category_clothes, R.color.categ_color_clothes, ""),
        Category("Vehiculos", R.drawable.ic_category_vehicle, R.color.categ_color_vehicles, ""),
        Category("Mantenimiento vehiculos", R.drawable.ic_category_vehicle_maintenance, R.color.categ_color_vehicles_maintenance, ""),
        Category("Recitales y eventos", R.drawable.ic_category_concerts, R.color.categ_color_concerts, ""),
        Category("Salud", R.drawable.ic_category_health, R.color.categ_color_health, ""),
        Category("Estudios particulares", R.drawable.ic_category_particular_studies, R.color.categ_color_studies, ""),
        Category("Medicamentos e insumos", R.drawable.ic_category_medicine, R.color.categ_color_medicine, ""),
        Category("Hobbies", R.drawable.ic_category_hobbies, R.color.categ_color_hobbies, ""),
        Category("Pintura, dibujo y fotografia", R.drawable.ic_category_painting_drawing_and_photography, R.color.categ_color_painting_drawing_photos, ""),
        Category("Inversiones y finanzas", R.drawable.ic_category_investment_and_finances, R.color.categ_color_investment_and_finances, ""),
        Category("Salario", R.drawable.ic_category_salary, R.color.categ_color_salary, ""),
        Category("Otros", R.drawable.ic_other_generic, R.color.categ_color_other, ""),
    )

    init{
        categories.forEach{ category ->
            categoriesIds.add(category.categoryName)
        }
        records = db.recsDao().getAll().toMutableList()
        records.sort()
        balances = db.balancesDao().getAll().toMutableList()
    }

    fun registerListener(listener: ()->Unit){
        listeners.add(listener)
    }

    fun unregisterListener(listener: ()->Unit){// TODO llamar cuando se vuelve para atrás
        listeners.remove(listener)
    }

    /**
     * updateTotalBalance()
     * return : Int
     * description
     * Called when HomeStartFragment is resumed or when addRecord is performed. It checks that "totals" record is present in BALANCES table.
     * If that record is present, updates it getting the total amount among the current operations' records.
     * To achieve this, this method iterates through the whole records list getting the total amount and the
     * transactions qtty.
     * If no totals record is present in BALANCES, manages an error and returns.
     **/

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
        records.sort()
        listeners.forEach{ it.invoke()}
        db.recsDao().insert(record)// TODO no deberia ir despues del bloque de abajo?
        if(db.balancesDao().countBalances() == 0) {
            Log.d("Debugger", "Initialize balances table")
            db.balancesDao().insertIntoBalances(Balance(App.context.getString(R.string.text_db_tag_totals), 0, 0.0))
        }
        updateCategoryBalance(record)
        updateTotalBalance()
    }

    fun deleteAllRecords(){
        records.clear()
        listeners.forEach{ it.invoke()}
        db.recsDao().deleteAll()
        db.balancesDao().deleteAll()
        db?.balancesDao()?.insertIntoBalances(Balance(App.context.getString(R.string.text_db_tag_totals), 0, 0.0))
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

    fun convertToCategory(categoryString: String): Category {
        var retCategory: Category
        //val provider = RecordsProvider.getProvider() y que hacemos con la doble instancia del provider?
        when(categoryString.trim()){
            "Comida y alimentos"-> retCategory = categories[0]
            "Restaurant y comida rapida"-> retCategory = categories[1]
            "Ropa"-> retCategory = categories[2]
            "Vehiculos"-> retCategory = categories[3]
            "Mantenimiento vehiculos"-> retCategory = categories[4]
            "Recitales y eventos"-> retCategory = categories[5]
            "Salud"-> retCategory = categories[6]
            "Estudios particulares"-> retCategory = categories[7]
            "Medicamentos e insumos"-> retCategory = categories[8]
            "Hobbies"-> retCategory = categories[9]
            "Pintura, dibujo y fotografia"-> retCategory = categories[10]
            "Inversiones y finanzas"-> retCategory = categories[11]
            "Salario"-> retCategory = categories[12]
            else -> retCategory = categories[13]
        }
        return retCategory
    }
}