package nl.macmannes.biedermeier


/**
 * Created by andre on 22-01-18.
 */
interface OnBackPressedBehaviour : ActivityBehaviour {
    fun onBackPressed(): Boolean
}