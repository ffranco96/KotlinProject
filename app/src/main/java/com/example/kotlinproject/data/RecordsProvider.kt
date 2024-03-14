package com.example.kotlinproject.data

class RecordsProvider {
    companion object {
        private val instance = RecordsProvider() // used to be available only from inside
        fun getProvider(): RecordsProvider {
            return instance
        }
    }

    private val listeners = mutableListOf<()->Unit>()

    fun registerListener(listener: ()->Unit){
        listeners.add(listener)
    }

    private val records = mutableListOf(
        Record("80.000,15","Entrada La vela puerca", "Recitales y jodas"),
        Record("110.000,65","Entrada Divididos", "Recitales y jodas")
    )

    // Cada vez que se agregan las notas, se ejecutan los listeners tambien
    fun addRec(record: Record){
        records.add(record)
        listeners.forEach{
            it.invoke() // For each listener, executes lambda function
        }
    }

    fun listAll():MutableList<Record>{
        return records
    }

    fun get(i : Int) : Record? {
        if (i >= 0){
            return records[i]
        } else {
            return null
        }
    }
}