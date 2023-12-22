package flyingshooter.shared.presentation

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.CallSetup
import io.ktor.server.application.hooks.ResponseSent
import io.ktor.util.AttributeKey
import org.koin.core.KoinApplication
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.ktor.plugin.KOIN_ATTRIBUTE_KEY
import org.koin.ktor.plugin.KOIN_SCOPE_ATTRIBUTE_KEY
import org.koin.ktor.plugin.KOIN_SCOPE_KEY
import org.koin.ktor.plugin.KoinApplicationStarted
import org.koin.ktor.plugin.KoinApplicationStopPreparing
import org.koin.ktor.plugin.KoinApplicationStopped
import org.koin.ktor.plugin.RequestScope


/*
    The default Koin plugin generates an independent KoinApplication, resulting in two distinct Koin instances: one within the Android context and another in the current context.
    This custom plugin utilizes the pre-existing KoinApplication injected earlier in the global Koin context (eg in App.kt for android and in future from server main() for appserver)
    It also links to serverScope, and removes koin callbacks as in original koin-ktor plugin
 */
fun getAppLinkedKoin(serverScope: Scope) =
    createApplicationPlugin(
        name = "Koin",
        createConfiguration = { getKoin().get<KoinApplication>() }) {
        val koinApplication = pluginConfig
        application.attributes.put(KOIN_ATTRIBUTE_KEY, koinApplication)
        on(CallSetup) { call ->
            val scopeComponent = RequestScope(koinApplication.koin).also {
                it.scope.linkTo(serverScope)
            }
            call.attributes.put(KOIN_SERVER_SCOPE_ATTRIBUTE_KEY, scopeComponent.scope)
        }
        on(ResponseSent) { call ->
            call.attributes[KOIN_SERVER_SCOPE_ATTRIBUTE_KEY].close()
        }
    }

val ApplicationCall.serverScope: Scope get() = this.attributes[KOIN_SERVER_SCOPE_ATTRIBUTE_KEY]
val KOIN_SERVER_SCOPE_ATTRIBUTE_KEY = AttributeKey<Scope>(KOIN_SCOPE_KEY)
