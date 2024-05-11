package com.example.kotlinproject.model

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

    private val records = mutableListOf(
        Rec(80000.15,"Entrada La vela puerca", "Aca saque la entrada de la vela puerca", "Recitales y jodas", "2024-05-25"),
        Rec(110000.65,"Entrada Divididos", "Aca saque la entrada de divididos","Recitales y jodas", "2024-03-20"),
        Rec(110000.65,"Entrada La Renga", "Aca saque la entrada de La Renga","Recitales y jodas", "2024-03-20"),
        Rec(110000.65,"Entrada Los Piojos", "Aca saque la entrada de Los Piojos","Recitales y jodas", "2024-03-20"),
        Rec(110000.65,"Entrada NTVG", "Aca saque la entrada de NTVG","Recitales y jodas", "2024-03-20"),
        Rec(110000.65,"Entrada Los Redondos", "Aca saque la entrada de Los Redondos","Recitales y jodas", "2024-03-20"),
        Rec(110000.65,"Entrada Airbag", "Aca saque la entrada de Airbag","Recitales y jodas", "2024-03-20"),
        Rec(110000.65,"Entrada Soda Stereo", "Aca saque la entrada de Soda Stereo","Recitales y jodas", "2024-03-20"),
        Rec(110000.65,"Entrada Pescado Rabioso", "Aca saque la entrada de Pescado Rabioso","Recitales y jodas", "2024-03-20"),
        Rec(110000.65,"Entrada Jovenes Pordioseros", "Aca saque la entrada de Jovenes Pordioseros","Recitales y jodas", "2024-03-20"),
        Rec(110000.65,"Entrada Sueño de Pescado", "Aca saque la entrada de Sueño de Pescado","Recitales y jodas", "2024-03-20"),
    )

    // Every time notes are added, also listeners are executed
    fun addRec(record: Rec){
        records.add(record)
        listeners.forEach{
            it.invoke() // For each listener, executes lambda function
        }
    }

    fun listAll():MutableList<Rec>{
        return records
    }

    fun get(i : Int) : Rec? {
        if (i >= 0){
            return records[i]
        } else {
            return null
        }
    }
}