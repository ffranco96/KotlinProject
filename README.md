Doc:
/** 
updateTotalBalance():
Called when HomeStartFragment is resumed or when addRecord is performed. It checks that "totals" record is present in BALANCES table. 
If that record is present, updates it getting the total amount among the current operations' records.
To achieve this, this method iterates through the whole records list getting the total amount and the
transactions qtty.
If no totals record is present in BALANCES, manages an error and returns.
**/

Switch:
Added switch. Now amount depends on switch: left (off) is an expense. Rigth (on) is an income.

Graphs:
At least one category must be operated to add at least 1 category record in BALANCES TABLE.
This must happen to enable Graphs View button on home screen. Otherwise, button must be grey.