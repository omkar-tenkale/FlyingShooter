package flyingshooter.android.data.datasource.local.sharedpreferences

import android.content.Context
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gson
import com.google.gson.Gson

//Class obfuscation Ignored in proguard
class DefaultPrefRepository(context: Context) : KotprefModel(context), PrefRepository {
    init {
        Kotpref.gson = Gson()
    }

    override val commitAllPropertiesByDefault: Boolean = true
    override val kotprefName: String = context.packageName + ".prefs"

    override var apiToken by nullableStringPref()
}