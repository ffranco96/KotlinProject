# Wallet de administración de ingresos
## V1.0.0
-----------------------------
El proyecto consiste en una aplicación de administración de finanzas personales, la cual le permitirá al usuario organizar sus ingresos y gastos. 

## **Características**:
* Posibilidad de clasificar ingresos y gastos para llevar un orden y un conteo de transacciones por categoría.
* Balance de totales que le permitirá al usuario realizar comparaciones.
* Gráficos para acompañar los balances totales, para dar información visual y permitir un eficiente análisis de datos.
* Categorías que se adaptan al día a día de una persona física promedio. Posibilidad de añadir categorías personalizadas.

## **Próximas versiones**
* Feature para importar transacciones a través de un archivo .csv .
* Feature para exportar transacciones a un nuevo archivo .csv .
* Conversión de monedas: de ARS a dolar MEP, dolar BLUE y dolar CCL.
* Visor de valores de criptoactivos ent iempo real.
* Cálculo de PyG (Pérdidas y ganancias).

### Documentation:
/**
updateTotalBalance():
Called when HomeStartFragment is resumed or when addRecord is performed. It checks that "totals" record is present in BALANCES table.
If that record is present, updates it getting the total amount among the current operations' records.
To achieve this, this method iterates through the whole records list getting the total amount and the
transactions qtty.
If no totals record is present in BALANCES, manages an error and returns.
**/

### Switch:
Added switch. Now amount depends on switch: left (off) is an expense. Rigth (on) is an income.

### Notes:
At least one category must be operated to add at least 1 category record in BALANCES TABLE.
This must happen to enable Graphs View button on home screen. Otherwise, button must be grey.