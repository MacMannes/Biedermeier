package nl.macmannes.biedermeier

import android.app.Activity
import java.lang.ref.WeakReference

/**
 * Created by andre on 01-03-17.
 */
open class BaseActivityBehaviour private constructor() : ActivityBehaviour {
    lateinit private var weakReference: WeakReference<Activity>
    override val activity: Activity?
        get() = weakReference.get()

    constructor(activity: Activity) : this() {
        weakReference = WeakReference(activity)
    }

}