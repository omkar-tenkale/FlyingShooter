dependencies {
  implementation(kotlin("stdlib"))

  testImplementation(rootProject.extra["konsist"]!!)
  testImplementation(rootProject.extra["kotest"]!!)

  api("io.insert-koin:koin-core:${rootProject.extra["koinVersion"]!!}")
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
}