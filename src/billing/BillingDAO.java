/*
 * ============================================================================
 * FILE: BillingDAO.java (Package: billing)
 * ============================================================================
 * PURPOSE:
 * Handle all database operations related to creating customer bills, batch-wise
 * FEFO (First Expired, First Out) stock deduction, discounts, free issues,
 * bill cancellations with stock reversal, and retrieving billing details.
 * 
 * ----------------------------------------------------------------------------
 * GENERAL RULES & DATABASE STANDARDS:
 * ----------------------------------------------------------------------------
 * - Always use PreparedStatements to prevent SQL injection.
 * - Always verify that the database connection is not null before executing
 *   queries (throw an SQLException if null).
 * - Use SQL Transactions (setAutoCommit(false), commit(), rollback()) for
 *   multi-step operations like creating or canceling a bill to ensure
 *   all-or-nothing data safety.
 * - Always reset auto-commit to true and close connections in a finally block.
 * - Trim extra spaces from string inputs (medicine names, reasons, etc.).
 * 
 * ----------------------------------------------------------------------------
 * WHAT THIS FILE MUST DO (REQUIRED METHODS & LOGIC):
 * ----------------------------------------------------------------------------
 * 1. CREATE A NEW BILL (WITH FEFO, SECURITY & TRANSACTION SAFETY):
 *    - Accept a Bill object containing a list of BillItems.
 *    - Validate that the Bill object and its items list are not null or empty.
 *    - SECURITY CHECK 1 (Free Issue): If the bill is marked as a Free Issue,
 *      verify that the current session user is an OWNER. If not, throw an
 *      UnauthorizedAccessException (UI will handle Owner override).
 *    - MERGE DUPLICATE ITEMS: If the same medicine appears on multiple rows in
 *      the bill, combine their quantities into a single total per medicine.
 *      Ensure every item quantity is greater than 0.
 *    - START DATABASE TRANSACTION:
 *      * Loop through each unique medicine requested:
 *        a) Fetch active, non-expired batches sorted by earliest expiry date
 *           first (FEFO: stock_quantity > 0 AND exp_date > today).
 *        b) If no active batches exist, check if only expired stock exists.
 *           Throw ExpiredMedicineException if expired stock is found;
 *           otherwise throw OutOfStockException.
 *        c) SECURITY CHECK 2 (Prescription): If the medicine requires a
 *           prescription, verify that 'isPrescriptionChecked' is true OR the
 *           current user is an OWNER. Otherwise, throw UnauthorizedAccessException.
 *        d) Check if the combined stock across all active batches is enough to
 *           fulfill the requested quantity. If not, throw OutOfStockException.
 *        e) FEFO DEDUCTION LOOP: Deduct stock starting from the batch expiring
 *           soonest. If one batch does not have enough, take all of it and move
 *           to the next batch until the requested quantity is fulfilled.
 *        f) Record each batch deduction as a finalized BillItem (capturing the
 *           exact batch medicine_id, unit price, deducted quantity, and line total)
 *           and update the inventory table stock inside the transaction.
 *      * CALCULATE TOTALS & DISCOUNTS:
 *        a) If Free Issue: Discount equals full total amount, and Net Total is 0.0.
 *        b) Normal Bill: If a valid Discount Reason is selected (not null, not
 *           empty, and not "None"), apply a 5% discount (rounded to 2 decimal
 *           places). Net Total = Total Amount - Discount Amount.
 *        c) Update the Bill object with calculated total, discount, and net total.
 *      * SAVE TO DATABASE:
 *        a) Insert the bill record into the 'bills' table (using the active
 *           session staff ID as cashier_id) and retrieve the generated bill_id.
 *        b) Batch-insert all finalized batch line items into 'bill_item'.
 *      * COMMIT TRANSACTION (or rollback if any error occurs).
 *    - POST-BILLING ALERTS: After committing, check total remaining stock for
 *      each billed medicine via InventoryDAO to trigger low-stock alerts if
 *      needed (catch alert errors separately so they do not fail the bill).
 *    - Return the generated bill_id.
 * 
 * 2. CANCEL AN EXISTING BILL (OWNER ONLY + STOCK REVERSAL):
 *    - Accept a 'billId' and a cancellation 'reason'.
 *    - SECURITY CHECK: Only an OWNER is allowed to cancel bills. Throw an
 *      UnauthorizedAccessException if the current user is not an Owner.
 *    - Check if the bill is already recorded in 'canceled_bills'. If so, throw
 *      an IllegalStateException.
 *    - Validate and trim the cancellation reason (default to "Canceled by Owner"
 *      if empty, and limit length to 50 characters to match DB column size).
 *    - START DATABASE TRANSACTION:
 *      * Fetch all medicine_ids and quantities from 'bill_item' for this billId.
 *      * If no items are found, throw an IllegalArgumentException.
 *      * Restore the quantities back to the exact batches in the 'inventory'
 *        table (stock_quantity = stock_quantity + quantity).
 *      * Insert a record into 'canceled_bills' with bill_id, Owner's staff_id,
 *        and the cancellation reason.
 *      * COMMIT TRANSACTION (or rollback on failure).
 *    - POST-CANCEL NOTIFICATION: Send a "BILL_CANCELED" alert to
 *      NotificationManager stating the bill ID, Owner ID, and reason.
 *    - Return true upon success.
 * 
 * 3. GET ALL CANCELED BILLS (OWNER ONLY):
 *    - Verify that the current user is an OWNER (throw UnauthorizedAccessException
 *      if not).
 *    - Fetch all records from 'canceled_bills' ordered by 'canceled_at' descending
 *      and return them as a List of CanceledBill objects.
 * 
 * 4. GET BILL DETAILS BY ID (FOR RECEIPTS / VIEWING):
 *    - Accept a 'billId' and fetch the main bill record from the 'bills' table.
 *    - If found, join 'bill_item' with 'inventory' to fetch all line items along
 *      with their medicine names, attach them to the Bill object, and return it.
 *    - Return null if the bill ID does not exist.
 * 
 * ----------------------------------------------------------------------------
 * HELPER METHODS TO INCLUDE:
 * ----------------------------------------------------------------------------
 * - Check if a bill ID already exists in the 'canceled_bills' table.
 * - Fetch active, non-expired batches for a medicine name ordered by exp_date ASC.
 * - Check if a medicine only has expired stock remaining (stock > 0, exp_date <= today).
 * - Deduct a specific quantity from a batch in 'inventory' using an active transaction.
 * ============================================================================
 */
