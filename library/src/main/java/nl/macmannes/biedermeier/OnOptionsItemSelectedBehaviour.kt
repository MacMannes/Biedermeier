package nl.macmannes.biedermeier

import android.view.MenuItem

/**
 * Created by andre on 01-03-17.
 */
interface OnOptionsItemSelectedBehaviour : ActivityBehaviour {
    fun onOptionsItemSelected(item: MenuItem): Boolean
}