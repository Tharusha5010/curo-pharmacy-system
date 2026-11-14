/*
 * ============================================================================
 * FILE: InventoryDAO.java (Package: inventory)
 * ============================================================================
 * PURPOSE:
 * Handle all database operations related to medicine inventory, batch tracking,
 * FEFO (First Expired, First Out) dispensing, stock deductions, and automated 
 * expiry/low-stock notifications.
 * 
 * ----------------------------------------------------------------------------
 * GENERAL RULES & DATABASE STANDARDS:
 * ----------------------------------------------------------------------------
 * - Always use PreparedStatements to prevent SQL injection.
 * - Always validate inputs (check for null objects, empty strings, or invalid 
 *   numbers) before running any database queries.
 * - Always verify that the database connection is not null before executing 
 *   queries (throw an SQLException if null).
 * - Use MSSQL date functions (e.g., CAST(GETDATE() AS DATE)) when checking for 
 *   non-expired stock directly in SQL queries.
 * - Trim extra spaces from string inputs (medicine names, batch numbers, etc.).
 * 
 * ----------------------------------------------------------------------------
 * WHAT THIS FILE MUST DO (REQUIRED METHODS & LOGIC):
 * ----------------------------------------------------------------------------
 * 1. ADD NEW MEDICINE OR RESTOCK BATCH:
 *    - Accept a Medicine object containing batch details.
 *    - Validate that the Medicine object, medicine name, and batch number are 
 *      not null or empty.
 *    - Set safe defaults if optional fields are missing:
 *        * Company Name -> "Unknown"
 *        * Moving Type -> "SLOW" (always store as uppercase: "FAST" or "SLOW")
 *        * Stock Date -> Today's date if not provided
 *    - Insert the new batch into the 'inventory' table.
 *    - CRITICAL: If insertion succeeds, immediately trigger both the Batch 
 *      Expiry check and the Total Stock check for this medicine so alerts are 
 *      generated right away if needed.
 *    - Return true if the record was successfully inserted.
 * 
 * 2. AUTO-SUGGEST / SMART SEARCH (FOR BILLING/POS):
 *    - Accept a partial search keyword (first few letters typed by the user).
 *    - Return an empty list immediately if the keyword is null or empty.
 *    - Fetch unique (DISTINCT) medicine names starting with the keyword 
 *      (use LIKE 'keyword%'), sorted alphabetically.
 *    - IMPORTANT FILTER: Only include medicines that currently have stock 
 *      greater than 0 AND are NOT expired (exp_date > today).
 * 
 * 3. GET BATCHES BY FEFO (FIRST EXPIRED, FIRST OUT):
 *    - Accept a specific medicine name.
 *    - Fetch all valid batches for that medicine where stock_quantity > 0 and 
 *      exp_date > today.
 *    - Order the results by 'exp_date' ascending (earliest expiry date first) 
 *      so older stock is sold before newer stock.
 * 
 * 4. GET ALL INVENTORY:
 *    - Fetch all records from the 'inventory' table (including out-of-stock or 
 *      expired batches for management viewing).
 *    - Order by medicine name ascending, then expiry date ascending.
 * 
 * 5. GET SINGLE BATCH BY ID:
 *    - Accept a 'medicine_id' and return the matching Medicine object, or null 
 *      if not found.
 * 
 * 6. UPDATE MOVING TYPE (FAST / SLOW):
 *    - Accept a medicine name and a new moving type.
 *    - Validate that the type is strictly either 'FAST' or 'SLOW' (case-insensitive).
 *    - Update the 'moving_type' across ALL batches belonging to that medicine name.
 *    - Return true if at least one row was updated.
 * 
 * 7. DEDUCT STOCK (FOR SALES / BILLING):
 *    - Accept a 'medicine_id' and 'quantitySold'. Validate that quantitySold > 0.
 *    - Deduct the sold quantity from 'stock_quantity' ONLY IF the current 
 *      stock_quantity is greater than or equal to quantitySold (prevent negative stock).
 *    - CRITICAL: If the update succeeds, fetch the updated medicine record and 
 *      immediately run the Total Stock Alert check to see if stock dropped 
 *      below the threshold.
 *    - Return true if stock was successfully deducted.
 * 
 * 8. CLEAR EXPIRED BATCH STOCK:
 *    - Accept a 'medicine_id' and set its 'stock_quantity' to 0 (used when 
 *      physically removing expired items from shelves).
 * 
 * 9. SYSTEM STARTUP ALERT SCAN:
 *    - Fetch all batches where stock_quantity > 0.
 *    - Loop through every batch and evaluate its expiry date individually.
 *    - Also evaluate total active stock per unique medicine name (use a Set 
 *      to track checked medicine names so duplicate low-stock alerts are NOT 
 *      sent if a medicine has multiple batches).
 * 
 * ----------------------------------------------------------------------------
 * ALERT EVALUATION HELPERS (PUSH TO NotificationManager):
 * ----------------------------------------------------------------------------
 * A. BATCH EXPIRY ALERT LOGIC:
 *    - Check the batch's expiry date against today's date:
 *        * If expired (exp_date <= today): Send an "EXPIRED" notification 
 *          instructing immediate removal from shelves (include batch number).
 *        * If expiring within 7 months (exp_date <= today + 7 months): Send an 
 *          "EXPIRY_WARNING" notification suggesting company re-export.
 * 
 * B. TOTAL ACTIVE STOCK ALERT LOGIC:
 *    - Calculate the SUM of 'stock_quantity' for all non-expired batches 
 *      (stock_quantity > 0 AND exp_date > today) of that medicine name.
 *    - Determine thresholds based on Moving Type:
 *        * If "FAST": Low threshold = 100 units | Suggested restock = 1000 units.
 *        * If "SLOW": Low threshold = 15 units  | Suggested restock = 100 units.
 *    - If total active stock <= low threshold, send a "LOW_STOCK" notification 
 *      stating the current total and the suggested restock amount.
 * ============================================================================
 */
