/*
 * ============================================================================
 * FILE: UserDAO.java (Package: auth)
 * ============================================================================
 * PURPOSE:
 * Handle all database operations related to staff authentication, account 
 * registration, role/status management, password updates, and owner overrides.
 * 
 * ----------------------------------------------------------------------------
 * GENERAL RULES & SECURITY:
 * ----------------------------------------------------------------------------
 * - Always use PreparedStatements to prevent SQL injection.
 * - Always validate inputs (check for nulls or empty strings) before running 
 *   any database queries.
 * - NEVER store or return actual passwords inside StaffMember objects when 
 *   loading user data into the session or returning staff lists (pass an empty 
 *   string "" for the password field).
 * - Always check if the database connection is null before executing queries.
 * 
 * ----------------------------------------------------------------------------
 * WHAT THIS FILE MUST DO (REQUIRED METHODS & LOGIC):
 * ----------------------------------------------------------------------------
 * 1. USER LOGIN:
 *    - Accept Staff ID and Password.
 *    - Check the database for a matching record where the account status is 
 *      strictly 'ACTIVE'.
 *    - If valid: Create a StaffMember object (without the password), save it 
 *      to the active UserSession, and return the object.
 *    - If invalid, empty, or inactive: Throw an InvalidStaffIdException.
 * 
 * 2. REGISTER NEW STAFF (OWNER ONLY):
 *    - First, verify that the currently logged-in user is an 'OWNER' via 
 *      UserSession. If not, throw an UnauthorizedAccessException.
 *    - Validate that Staff ID and Password are provided.
 *    - Default the role to 'CASHIER' if left empty, and ensure it is strictly 
 *      either 'OWNER' or 'CASHIER'.
 *    - Default the status to 'ACTIVE' if left empty, and ensure it is strictly 
 *      either 'ACTIVE' or 'INACTIVE'.
 *    - Insert the new staff record into the database and return true if successful.
 * 
 * 3. UPDATE STAFF ACCOUNT STATUS (OWNER ONLY):
 *    - Verify that the current user is an 'OWNER'. Otherwise, throw an 
 *      UnauthorizedAccessException.
 *    - Accept a Staff ID and a new status ('ACTIVE' or 'INACTIVE').
 *    - IMPORTANT SAFEGUARD: Prevent the currently logged-in Owner from 
 *      deactivating ('INACTIVE') their own account.
 *    - Update the status in the database and return true if successful.
 * 
 * 4. GET ALL STAFF MEMBERS (OWNER ONLY):
 *    - Verify that the current user is an 'OWNER'. Otherwise, throw an 
 *      UnauthorizedAccessException.
 *    - Fetch all staff records from the database (excluding passwords) and 
 *      return them as a List of StaffMember objects.
 * 
 * 5. CHANGE PASSWORD:
 *    - Accept Staff ID, old password, and new password.
 *    - Authorization check: Allow the action ONLY if the logged-in user is 
 *      changing their own password, OR if the logged-in user is an 'OWNER'. 
 *      Otherwise, throw an UnauthorizedAccessException.
 *    - Update the password in the database only if both the Staff ID and the 
 *      old password match.
 *    - If no rows are updated (meaning old password was wrong or ID doesn't 
 *      exist), throw an InvalidStaffIdException.
 * 
 * 6. ONE-TIME OWNER OVERRIDE VERIFICATION:
 *    - Accept an Owner's Staff ID and Password (used when a Cashier needs 
 *      temporary Owner approval for restricted actions).
 *    - Check the database for a matching record where role is 'OWNER' and 
 *      status is 'ACTIVE'.
 *    - CRITICAL: Do NOT overwrite or change the current UserSession. Just 
 *      return the verified Owner's StaffMember object (or return null if 
 *      verification fails).
 * ============================================================================
 */
