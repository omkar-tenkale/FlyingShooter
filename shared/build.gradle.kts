dependencies {
  implementation(kotlin("stdlib"))

  testImplementation(rootProject.extra["konsist"]!!)
  testImplementation(rootProject.extra["kotest"]!!)

  api("io.insert-koin:koin-core:${rootProject.extra["koinVersion"]!!}")
  api("io.github.quillraven.fleks:Fleks:2.5")

  val gdxVersion = rootProject.extra["gdxVersion"]!!
  api("com.badlogicgames.gdx:gdx-box2d:$gdxVersion")
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
}