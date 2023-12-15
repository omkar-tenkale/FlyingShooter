import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("plugin.serialization") version "1.9.21"
}

val composeVersion = "1.5.0"

android {
  compileSdk = 34
  sourceSets {
    named("main") {
      manifest.srcFile("AndroidManifest.xml")
      java.srcDirs(listOf("src/main/java", "src/main/kotlin"))
      aidl.srcDirs(listOf("src/main/java", "src/main/kotlin"))
      renderscript.srcDirs(listOf("src/main/java", "src/main/kotlin"))
      res.srcDirs(listOf("res"))
      assets.srcDirs(listOf("../assets"))
      jniLibs.srcDirs(listOf("libs"))
    }
  }
  packagingOptions {
    resources {
      excludes += setOf(
        "META-INF/INDEX.LIST",
        "META-INF/io.netty.versions.properties",
        "META-INF/robovm/ios/robovm.xml",
        "META-INF/DEPENDENCIES.txt",
        "META-INF/DEPENDENCIES",
        "META-INF/dependencies.txt",
        "**/*.gwt.xml"
      )
      pickFirsts += setOf(
        "META-INF/LICENSE.txt",
        "META-INF/LICENSE",
        "META-INF/license.txt",
        "META-INF/LGPL2.1",
        "META-INF/NOTICE.txt",
        "META-INF/NOTICE",
        "META-INF/notice.txt"
      )
    }
  }
  defaultConfig {
    applicationId = "dev.omkartenkale.flyingshooter"
    minSdkVersion(21)
    targetSdkVersion(34)
    versionCode = 1
    versionName = "1.0"
    multiDexEnabled = true
  }
  namespace = "app.flyingshooter"
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    isCoreLibraryDesugaringEnabled = true
  }
  kotlin {
    jvmToolchain {
      languageVersion.set(JavaLanguageVersion.of("17"))
    }
  }
  buildTypes {
    named("release") {
      isMinifyEnabled = true
      setProguardFiles(listOf(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"))
    }
  }
  buildFeatures {
    compose = true
    renderScript = true
    aidl = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = composeVersion
  }

}

repositories {
  // needed for AAPT2, may be needed for other tools
  google()
}

val natives by configurations.creating

dependencies {
  val gdxVersion = "1.11.0"
  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.2.2")
  implementation("com.badlogicgames.gdx:gdx-backend-android:$gdxVersion")
  implementation(project(":core"))

  testImplementation(rootProject.extra["konsist"]!!)
  testImplementation(rootProject.extra["kotest"]!!)

  implementation("androidx.compose.ui:ui:$composeVersion") {
    exclude(group= "com.google.guava", module= "listenablefuture")
  }
  implementation("androidx.activity:activity-compose:$composeVersion")
  implementation("androidx.fragment:fragment-ktx:$composeVersion")

  implementation("androidx.compose.material:material:$composeVersion")
//  implementation("androidx.ui:ui-tooling:0.1.0-dev17")

  val voyagerVersion = "1.0.0-rc10"
  implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
  implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
  implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion")

  val kotPrefVersionVersion = "2.13.1"
  implementation("com.google.code.gson:gson:2.10.1")
  implementation("com.chibatching.kotpref:kotpref:$kotPrefVersionVersion")
  implementation("com.chibatching.kotpref:enum-support:$kotPrefVersionVersion")
  implementation("com.chibatching.kotpref:gson-support:$kotPrefVersionVersion")
  implementation("com.chibatching.kotpref:gson-support:$kotPrefVersionVersion")

  val koinVersion = rootProject.extra["koinVersion"]!!
  api("io.insert-koin:koin-android:$koinVersion") {
    exclude(group= "com.google.guava", module= "listenablefuture")
  }
//  api("io.insert-koin:koin-compose:$koinVersion")
  api("io.insert-koin:koin-androidx-compose:$koinVersion")

  val ktorVersion = rootProject.extra["ktorVersion"]!!
  implementation("io.ktor:ktor-client-core:$ktorVersion")
  implementation("io.ktor:ktor-client-cio:$ktorVersion")
  implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
  implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
  implementation("io.ktor:ktor-client-websockets-jvm:$ktorVersion")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
  implementation("io.ktor:ktor-client-logging:$ktorVersion")
  implementation("io.ktor:ktor-client-auth:$ktorVersion")

  natives("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-arm64-v8a")
  natives("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-armeabi-v7a")
  natives("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-x86")
  natives("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-x86_64")
  natives("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-arm64-v8a")
  natives("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-armeabi-v7a")
  natives("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-x86")
  natives("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-x86_64")
  natives("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-arm64-v8a")
  natives("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-armeabi-v7a")
  natives("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-x86")
  natives("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-x86_64")
}

// Called every time gradle gets executed, takes the native dependencies of
// the natives configuration, and extracts them to the proper libs/ folders
// so they get packed with the APK.
tasks.register("copyAndroidNatives") {
  doFirst {
    file("libs/armeabi-v7a/").mkdirs()
    file("libs/arm64-v8a/").mkdirs()
    file("libs/x86_64/").mkdirs()
    file("libs/x86/").mkdirs()

    configurations.getByName("natives").copy().files.forEach { jar ->
      var outputDir: File? = null
      when {
        jar.name.endsWith("natives-armeabi-v7a.jar") -> outputDir = file("libs/armeabi-v7a")
        jar.name.endsWith("natives-arm64-v8a.jar") -> outputDir = file("libs/arm64-v8a")
        jar.name.endsWith("natives-x86_64.jar") -> outputDir = file("libs/x86_64")
        jar.name.endsWith("natives-x86.jar") -> outputDir = file("libs/x86")
      }
      outputDir?.let {
        copy {
          from(zipTree(jar))
          into(it)
          include("*.so")
        }
      }
    }
  }
}

tasks.matching { it.name.contains("merge") && it.name.contains("JniLibFolders") }.configureEach {
  dependsOn("copyAndroidNatives")
}

tasks.register<Exec>("run") {
  val localProperties = project.file("../local.properties")
  val path: String = if (localProperties.exists()) {
    val properties: Properties = Properties()
    localProperties.inputStream().use { instr ->
      properties.load(instr)
    }
    properties.getProperty("sdk.dir") ?: System.getenv("ANDROID_SDK_ROOT")
  } else {
    System.getenv("ANDROID_SDK_ROOT")
  }

  val adb = File(path, "platform-tools/adb")
  commandLine(adb.absolutePath, "shell", "am", "start", "-n", "dev.omkartenkale.flyingshooter/flyingshooter.android.AndroidLauncher")
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
}