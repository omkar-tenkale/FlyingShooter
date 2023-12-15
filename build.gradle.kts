import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

apply(from = "dependencies.gradle.kts")

buildscript {
  repositories {
    mavenCentral()
    mavenLocal()
    google()
    gradlePluginPortal()
    maven(url = "https://s01.oss.sonatype.org")
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
  }
  dependencies {
    classpath(kotlin("gradle-plugin", version = "1.9.0"))
    classpath("com.android.tools.build:gradle:8.1.0")
  }
}

allprojects {
  apply(plugin = "eclipse")
  apply(plugin = "idea")
}

configure(subprojects - project(":android")) {
  apply(plugin = "java-library")
  apply(plugin = "kotlin")
  tasks.withType<JavaCompile> {
    options.isIncremental = true
  }
  tasks.withType<KotlinCompile>().configureEach {

    kotlinOptions {
      jvmTarget = "11"
      apiVersion = "1.8"
      languageVersion = "1.8"
    }

    // you can also add additional compiler args,
    // like opting in to experimental features
    kotlinOptions.freeCompilerArgs += listOf(
      "-opt-in=kotlin.RequiresOptIn",
    )
  }
}

subprojects {
  version = "1.0.0"
  extra["appName"] = "FlyingShooter"
  repositories {
    mavenCentral()
    maven(url = "https://s01.oss.sonatype.org")
    mavenLocal()
    gradlePluginPortal()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven(url = "https://jitpack.io")
  }
}