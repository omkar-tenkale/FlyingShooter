plugins {
  kotlin("plugin.serialization") version "1.9.21"
}
dependencies {
  implementation(kotlin("stdlib"))

  testImplementation(rootProject.extra["konsist"]!!)
  testImplementation(rootProject.extra["kotest"]!!)

  api("io.insert-koin:koin-core:${rootProject.extra["koinVersion"]!!}")
  implementation("io.insert-koin:koin-ktor:${rootProject.extra["koinVersion"]!!}")
  api("io.github.quillraven.fleks:Fleks:2.5")

  val gdxVersion = rootProject.extra["gdxVersion"]!!
  api("com.badlogicgames.gdx:gdx-box2d:$gdxVersion")

  val ktorVersion = rootProject.extra["ktorVersion"]!!
  implementation("io.ktor:ktor-server-netty:$ktorVersion")
  implementation("io.ktor:ktor-server-core:$ktorVersion")
  implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
  implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
  implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
  implementation("io.ktor:ktor-server-websockets-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion") {
    exclude(group= "com.google.guava", module= "guava")
  }
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
}