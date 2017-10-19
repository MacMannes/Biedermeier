package nl.macmannes.biedermeier

import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
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
        behaviours.forEach { (it as? OnDestroyBehaviour)?.onDestroy() }
        removeBehaviours()
        super.onDestroy()
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        behaviours.forEach { (it as? OnBeforeCreateBehaviour)?.onCreate(savedInstanceState) }
        super.onCreate(savedInstanceState)
        behaviours.forEach { (it as? OnCreateBehaviour)?.onCreate(savedInstanceState) }
    }

    @CallSuper
    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        behaviours.forEach { (it as? OnPostCreateBehaviour)?.onPostCreate(savedInstanceState) }
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        behaviours.forEach { (it as? OnStartBehaviour)?.onStart() }
    }

    override fun onPause() {
        super.onPause()
        behaviours.forEach { (it as? OnPauseBehaviour)?.onPause() }
    }

    override fun onResume() {
        super.onResume()
        behaviours.forEach { (it as? OnResumeBehaviour)?.onResume() }
    }

    @CallSuper
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        behaviours.forEach { (it as? OnRequestPermissionsResultBehaviour)?.onRequestPermissionsResult(requestCode, permissions, grantResults) }
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

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        var handled = false

        behaviours.forEach {
            if (it is DispatchKeyEventBehaviour && !handled) {
                handled = it.dispatchKeyEvent(event)
            }
        }

        return if (handled) handled else super.dispatchKeyEvent(event)
    }

    //endregion
}