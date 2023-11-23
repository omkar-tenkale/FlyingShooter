import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import io.kotest.core.spec.style.FreeSpec

class CleanArchitectureTest : FreeSpec({
    "Every use case reside in use case package" {
        Konsist
            .scopeFromProject()
            .assertArchitecture {
                val coreDomain = Layer("Core Domain", "flyingshooter.core.domain..")
                val corePresentation = Layer("Core Presentation", "flyingshooter.core.presentation..")
                val coreData = Layer("Core Data", "flyingshooter.core.data..")

                val sharedDomain = Layer("Shared Domain", "flyingshooter.shared.domain..")
                val sharedPresentation = Layer("Shared Presentation", "flyingshooter.shared.presentation..")
                val sharedData = Layer("Shared Data", "flyingshooter.shared.data..")

                sharedDomain.dependsOnNothing()
                sharedPresentation.dependsOn(sharedDomain)
                sharedData.dependsOn(sharedDomain)

                coreDomain.dependsOn(sharedDomain)
                corePresentation.dependsOn(coreDomain)
                coreData.dependsOn(coreDomain)
            }
    }
})