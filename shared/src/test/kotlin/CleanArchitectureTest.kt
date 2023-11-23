import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import io.kotest.core.spec.style.FreeSpec

class CleanArchitectureTest : FreeSpec({
    "Every use case reside in use case package" {
        Konsist
            .scopeFromProject()
            .assertArchitecture {
                val domain = Layer("Domain", "flyingshooter.shared.domain..")
                val presentation = Layer("Presentation", "flyingshooter.shared.presentation..")
                val data = Layer("Data", "flyingshooter.shared.data..")

                domain.dependsOnNothing()
                presentation.dependsOn(domain)
                data.dependsOn(domain)
            }
    }
})