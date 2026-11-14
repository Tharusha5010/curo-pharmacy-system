/*
 * ============================================================================
 * FILE: NotificationManager.java (Package: alerts)
 * ============================================================================
 * PURPOSE:
 * Manage all app notifications in one central place and update the UI 
 * automatically whenever changes happen.
 * 
 * ----------------------------------------------------------------------------
 * WHAT THIS FILE MUST DO:
 * ----------------------------------------------------------------------------
 * 1. SINGLE SHARED INSTANCE:
 *    - Ensure only one active manager exists across the whole application.
 *    - Make sure it works safely even if multiple background tasks access it 
 *      at the same time.
 * 
 * 2. SAFE DATA STORAGE:
 *    - Keep a list of all notifications and a list of active UI screens.
 *    - Storage must be thread-safe because alerts can be triggered from 
 *      background processes.
 * 
 * 3. UI SUBSCRIPTION SYSTEM:
 *    - Allow UI screens to subscribe (register) to receive live alert updates.
 *    - Avoid adding duplicate or empty subscriptions.
 *    - Allow UI screens to unsubscribe when they close (to prevent memory leaks).
 * 
 * 4. ADDING & BROADCASTING ALERTS:
 *    - Accept alert details (type, title, message), save the new alert, and 
 *      immediately inform all subscribed UI screens.
 *    - IMPORTANT: Make sure all UI updates run safely on the Swing UI thread 
 *      (Event Dispatch Thread) so the interface never freezes or crashes.
 * 
 * 5. READING & CLEARING ALERTS:
 *    - Provide a way to view all saved alerts (return a safe copy so external 
 *      code cannot accidentally modify the original list).
 *    - Provide the total count of alerts (for showing badge numbers on icons).
 *    - Provide a way to clear all alerts at once and inform the UI screens to 
 *      refresh (again, safely on the Swing UI thread).
 * ============================================================================
 */
