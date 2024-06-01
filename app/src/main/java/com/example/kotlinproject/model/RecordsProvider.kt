package com.example.kotlinproject.model

import androidx.room.Room
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
    init{
        records = db.recsDao().getAll().toMutableList()
        balances = db.balancesDao().getAll().toMutableList()
    }

    private val listeners = mutableListOf<()->Unit>()

    fun registerListener(listener: ()->Unit){
        listeners.add(listener)
    }

    private val categories = mutableListOf(
        Category("Comida y alimentos", R.drawable.ic_category_food, R.color.sad_grey, ""),
        Category("Restaurant y comida rapida", R.drawable.ic_category_fast_food, R.color.sad_grey, ""),
        Category("Ropa", R.drawable.ic_category_clothes, R.color.sad_grey, ""),
        Category("Vehículos", R.drawable.ic_category_vehicle, R.color.sad_grey, ""),
        Category("Mantenimiento vehiculos", R.drawable.ic_category_vehicle_maintenance, R.color.sad_grey, ""),
        Category("Recitales y eventos", R.drawable.ic_category_concerts, R.color.sad_grey, ""),
        Category("Salud", R.drawable.ic_category_health, R.color.sad_grey, ""),
        Category("Estudios particulares", R.drawable.ic_category_particular_studies, R.color.sad_grey, ""),
        Category("Medicamentos e insumos", R.drawable.ic_category_medicine, R.color.sad_grey, ""),
        Category("Hobbies", R.drawable.ic_category_hobbies, R.color.sad_grey, ""),
        Category("Pintura, dibujo y fotografía", R.drawable.ic_category_painting_drawing_and_photography, R.color.sad_grey, ""),
        Category("Inversiones y finanzas", R.drawable.ic_category_investment_and_finances, R.color.sad_grey, ""),
        Category("Salario", R.drawable.ic_category_salary, R.color.sad_grey, ""),
        Category("Otros", R.drawable.ic_other_generic, R.color.sad_grey, ""),
    )

    private val categoriesIds = mutableListOf<String>("Comida y alimentos", "Restaurant y comida rapida", "Ropa",
    "Vehiculos", "Matenimiento vehiculos", "Recitales y eventos", "Salud", "Estudios particulares", "Medicaments e insumos",
    "Hobbies", "Pintura, dibujo y fotografia", "Inversiones y finanzas", "Salario") //@todo quiza se pueda hacer con los ids tomados del otro mutableList

    // Every time notes are added, also listeners are executed
    fun addRecord(record: Rec){
        records.add(record)
        listeners.forEach{
            it.invoke() // For each listener, executes lambda function
            db.recsDao().insert(record)
        }
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
}