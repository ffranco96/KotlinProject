package com.example.kotlinproject.model

import com.example.kotlinproject.R

class RecordsProvider {
    companion object {
        private val instance = RecordsProvider() // used to be available only from inside. Singleton.
        fun getProvider(): RecordsProvider {
            return instance
        }
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
        Category("Salud", R.drawable.ic_category_health_2, R.color.sad_grey, ""),
        Category("Estudios particulares", R.drawable.ic_category_particular_studies, R.color.sad_grey, ""),
        Category("Medicamentos e insumos", R.drawable.ic_category_medicine, R.color.sad_grey, ""),
        Category("Hobbies", R.drawable.ic_category_hobbies, R.color.sad_grey, ""),
        Category("Pintura, dibujo y fotografía", R.drawable.ic_category_painting_drawing_and_photography, R.color.sad_grey, ""),
        Category("Inversiones y finanzas", R.drawable.ic_category_investment_and_finances_2, R.color.sad_grey, ""),
        Category("Salario", R.drawable.ic_category_salary_2, R.color.sad_grey, ""),
        Category("Otros", R.drawable.ic_other_generic, R.color.sad_grey, ""),
    )

    private val categoriesIds = mutableListOf<String>("Comida y alimentos", "Restaurant y comida rapida", "Ropa",
    "Vehiculos", "Matenimiento vehiculos", "Recitales y eventos", "Salud", "Estudios particulares", "Medicaments e insumos",
    "Hobbies", "Pintura, dibujo y fotografia", "Inversiones y finanzas", "Salario") //@todo quiza se pueda hacer con los ids tomados del otro mutableList

    private val records = mutableListOf(
        Rec(80000.15,"Club de la milanesa", "Paga puchi a puchi", categories[1], "2024-05-25", "ARS"),
        Rec(110000.65,"Modafinilo", "Paga la clau",categories[8], "2024-03-20", "ARS"),
        Rec(190000.9,"Estudio del sueño", "estudio de Apneas en el IFN",categories[7], "2024-03-20", "ARS"),
        Rec(110000.65,"Fideos Don vicente", "Cocina puchi",categories[0], "2024-03-20", "ARS"),
        Rec(110000.65,"Entrada NTVG", "Aca saque la entrada de NTVG",categories[5], "2024-03-20", "ARS"),
        Rec(110000.65,"Neumaticos", "Se hicieron pelota en Cordoba",categories[4], "2024-03-20", "ARS"),
        //Rec(110000.65,"Entrada Airbag", "Aca saque la entrada de Airbag","Recitales y jodas", "2024-03-20", "ARS"),
        //Rec(110000.65,"Entrada Soda Stereo", "Aca saque la entrada de Soda Stereo","Recitales y jodas", "2024-03-20", "ARS"),
        //Rec(110000.65,"Entrada Pescado Rabioso", "Aca saque la entrada de Pescado Rabioso","Recitales y jodas", "2024-03-20", "ARS"),
        //Rec(110000.65,"Entrada Jovenes Pordioseros", "Aca saque la entrada de Jovenes Pordioseros","Recitales y jodas", "2024-03-20", "ARS"),
        //Rec(110000.65,"Entrada Sueño de Pescado", "Aca saque la entrada de Sueño de Pescado","Recitales y jodas", "2024-03-20", "ARS"),
    )

    // Every time notes are added, also listeners are executed
    fun addRecord(record: Rec){
        records.add(record)
        listeners.forEach{
            it.invoke() // For each listener, executes lambda function
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