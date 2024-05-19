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
        Category("Comida y alimentos", R.drawable.ic_other_generic, R.color.sad_grey, ""),
        Category("Restaurant y comida rapida", R.drawable.ic_category_concerts_1, R.color.sad_grey, ""),
        Category("Ropa", R.drawable.ic_category_concerts_2, R.color.sad_grey, ""),
        Category("Vehículos", R.drawable.ic_category_concerts_3, R.color.sad_grey, ""),
        Category("Mantenimiento vehiculos", R.drawable.ic_category_concerts_4, R.color.sad_grey, ""),
        Category("Recitales y eventos", R.drawable.ic_category_concerts_5, R.color.sad_grey, ""),
        Category("Estudios particulares", R.drawable.ic_category_concerts_6, R.color.sad_grey, ""),
        Category("Medicamentos e insumos", R.drawable.ic_other_generic, R.color.sad_grey, ""),
        Category("Hobbies", R.drawable.ic_other_generic, R.color.sad_grey, ""),
        Category("Pintura, dibujo y fotografía", R.drawable.ic_other_generic, R.color.sad_grey, ""),
        Category("Inversiones y finanzas", R.drawable.ic_other_generic, R.color.sad_grey, ""),
        Category("Salario", R.drawable.ic_other_generic, R.color.sad_grey, ""),
        Category("Otros", R.drawable.ic_other_generic, R.color.sad_grey, ""),
    )

    private val categoriesIds = mutableListOf<String>("Comida y alimentos", "Restaurant y comida rapida", "Ropa",
    "Vehiculos", "Matenimiento vehiculos", "Recitales y eventos", "Salud", "Estudios particulares", "Medicaments e insumos",
    "Hobbies", "Pintura, dibujo y fotografia", "Inversiones y finanzas", "Salario") //@todo quiza se pueda hacer con los ids tomados del otro mutableList

    private val records = mutableListOf(
        Rec(80000.15,"Entrada La vela puerca", "Aca saque la entrada de la vela puerca", categories[1], "2024-05-25", "ARS"),
        Rec(110000.65,"Entrada Divididos", "Aca saque la entrada de divididos",categories[2], "2024-03-20", "ARS"),
        Rec(190000.9,"Entrada La Renga", "Aca saque la entrada de La Renga",categories[3], "2024-03-20", "ARS"),
        Rec(110000.65,"Entrada Los Piojos", "Aca saque la entrada de Los Piojos",categories[4], "2024-03-20", "ARS"),
        Rec(110000.65,"Entrada NTVG", "Aca saque la entrada de NTVG",categories[5], "2024-03-20", "ARS"),
        Rec(110000.65,"Entrada Los Redondos", "Aca saque la entrada de Los Redondos",categories[0], "2024-03-20", "ARS"),
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