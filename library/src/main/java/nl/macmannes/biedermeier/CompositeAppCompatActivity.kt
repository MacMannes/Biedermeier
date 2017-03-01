package nl.macmannes.biedermeier

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity

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
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        behaviours.forEach { if (it is OnRequestPermissionsResultBehaviour) it.onRequestPermissionsResult(requestCode, permissions, grantResults) }
    }

    //endregion
}