# Wallet de administración de ingresos
## V1.0.0
-----------------------------
El proyecto consiste en una aplicación de administración de finanzas personales, la cual le permitirá al usuario organizar sus ingresos y gastos. 

## **Características**:
* Posibilidad de clasificar ingresos y gastos para llevar un orden y un conteo de transacciones por categoría.
* Balance de totales que le permitirá al usuario llevar un total de sus gastos e ingresos.
* Gráficos para acompañar los balances totales, para dar información visual y permitir un eficiente análisis de datos.
* Categorías que se adaptan al día a día de una persona física promedio.
* Feature para importar transacciones a través de un archivo .csv .

## **Próximas versiones**
* Posibilidad de añadir categorías personalizadas.
* Posibilidad de agregar un .csv personalizado.
* Feature para exportar transacciones a un nuevo archivo .csv .
* Conversión de monedas: de ARS a dolar MEP, dolar BLUE y dolar CCL.
* Visor de valores de criptoactivos ent iempo real.
* Cálculo de PyG (Pérdidas y ganancias).

### Instrucciones de uso:

### Switch:
Se agregó un switch para setear la transacción como un ingreso o un gasto.
El monto dependerá de ese switch para ser negativo en caso de un gasto (switch hacia la izquierda)
o ser positivo en caso de un ingreso (switch hacia la derecha).

### Database:
Las transacciones se contabilizarán en dos tablas: Records y Balances. 
La primera contendrá todas las transacciones de todas las categorías.
La segunda, poseerá un registro por cada categoría con el monto total y la cantidad de 
transacciones para esa categoría.

### Gráficos
El botón de gráficos sólo dejará de estar grisado en cuanto se agregue el primer registro.