plugins {
  application
}

val appName: String by project

application{
  mainClassName = "flyingshooter.server.ServerLauncher"
}
eclipse {
  project.name = "$appName-server"
}

dependencies {
  implementation(project(":shared"))

  testImplementation(rootProject.extra["konsist"]!!)
  testImplementation(rootProject.extra["kotest"]!!)
}

tasks.jar {
  archiveBaseName.set(appName)
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
  dependsOn(configurations.runtimeClasspath)
//  from(configurations.runtimeClasspath.files.collect { it.isDirectory() ? it : zipTree(it) }) {
//    exclude("META-INF/INDEX.LIST", "META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
//    exclude("META-INF/maven/**")
//  }
  from(configurations.runtimeClasspath.get().filter { it.isDirectory() || it.name.endsWith(".jar") }
    .map { if (it.isDirectory()) it else zipTree(it) }) {
    exclude("META-INF/INDEX.LIST", "META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
    exclude("META-INF/maven/**")
  }
  manifest {
    attributes["Main-Class"] = "flyingshooter.server.ServerLauncher"
  }
  doLast {
    file(archiveFile).setExecutable(true, false)
  }
}

tasks.register("dist") {
  dependsOn(tasks.jar)
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
}