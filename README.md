Doc:
/** 
updateTotalBalance():
Called when HomeStartFragment is resumed or when addRecord is performed. It checks that "totals" record is present in BALANCES table. 
If that record is present, updates it getting the total amount among the current operations' records. 
If not, manages an error and returns.
**/

Switch:
Added switch. Now amount depends on switch: left (off) is an expense. Rigth (on) is an income.