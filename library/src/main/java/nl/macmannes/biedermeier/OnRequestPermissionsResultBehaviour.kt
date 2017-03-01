package nl.macmannes.biedermeier

/**
 * Created by andre on 01-03-17.
 */
interface OnRequestPermissionsResultBehaviour : ActivityBehaviour {
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
}