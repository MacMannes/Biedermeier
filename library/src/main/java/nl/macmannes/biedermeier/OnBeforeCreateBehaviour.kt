package nl.macmannes.biedermeier

import android.os.Bundle

/**
 * Created by andre on 01-03-17.
 */
interface OnBeforeCreateBehaviour : ActivityBehaviour {
    fun onCreate(savedInstanceState: Bundle?)
}