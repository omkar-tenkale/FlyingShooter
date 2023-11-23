dependencies {
  implementation(kotlin("stdlib"))

  testImplementation(rootProject.extra["konsist"]!!)
  testImplementation(rootProject.extra["kotest"]!!)
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
}