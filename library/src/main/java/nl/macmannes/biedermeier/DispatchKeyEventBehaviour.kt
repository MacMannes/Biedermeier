package nl.macmannes.biedermeier

import android.view.KeyEvent

/**
 * Created by andre on 01-03-17.
 */
interface DispatchKeyEventBehaviour : ActivityBehaviour {
    fun dispatchKeyEvent(event: KeyEvent): Boolean
}