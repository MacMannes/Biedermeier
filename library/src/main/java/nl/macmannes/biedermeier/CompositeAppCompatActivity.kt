package nl.macmannes.biedermeier

import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

/**
 * Created by andre on 01-03-17.
 */
open class CompositeAppCompatActivity : AppCompatActivity() {
    private val behaviours: MutableList<ActivityBehaviour> = mutableListOf()

    fun addBehaviour(behaviour: ActivityBehaviour) {
        behaviours.add(behaviour)
    }

    protected fun removeBehaviours() {
        behaviours.clear()
    }

    //region Behaviours

    @CallSuper
    override fun onDestroy() {
        behaviours.forEach { if (it is OnDestroyBehaviour) it.onDestroy() }
        removeBehaviours()
        super.onDestroy()
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        behaviours.forEach { if (it is OnBeforeCreateBehaviour) it.onCreate(savedInstanceState) }
        super.onCreate(savedInstanceState)
        behaviours.forEach { if (it is OnCreateBehaviour) it.onCreate(savedInstanceState) }
    }

    @CallSuper
    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        behaviours.forEach { if (it is OnPostCreateBehaviour) it.onPostCreate(savedInstanceState) }
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        behaviours.forEach { if (it is OnStartBehaviour) it.onStart() }
    }

    override fun onPause() {
        super.onPause()
        behaviours.forEach { if (it is OnPauseBehaviour) it.onPause() }
    }

    override fun onResume() {
        super.onResume()
        behaviours.forEach { if (it is OnResumeBehaviour) it.onResume() }
    }

    @CallSuper
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        behaviours.forEach { if (it is OnRequestPermissionsResultBehaviour) it.onRequestPermissionsResult(requestCode, permissions, grantResults) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var handled = false
        behaviours.forEach {
            if (it is OnOptionsItemSelectedBehaviour && !handled) {
                handled = it.onOptionsItemSelected(item)
            }
        }

        return if (handled) handled else super.onOptionsItemSelected(item)
    }

    //endregion
}