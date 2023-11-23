import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
  compileSdk = 32
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
    // Preventing from license violations (more or less):
    pickFirst("META-INF/LICENSE.txt")
    pickFirst("META-INF/LICENSE")
    pickFirst( "META-INF/license.txt")
    pickFirst( "META-INF/LGPL2.1")
    pickFirst( "META-INF/NOTICE.txt")
    pickFirst( "META-INF/NOTICE")
    pickFirst( "META-INF/notice.txt")
    // Excluding unnecessary meta-data:
    exclude("META-INF/robovm/ios/robovm.xml")
    exclude("META-INF/DEPENDENCIES.txt")
    exclude("META-INF/DEPENDENCIES")
    exclude("META-INF/dependencies.txt")
    // These are only used by GWT, and not Android.
    exclude("**/*.gwt.xml")
  }
  defaultConfig {
    applicationId = "dev.omkartenkale.flyingshooter"
    minSdkVersion(19)
    targetSdkVersion(32)
    versionCode = 1
    versionName = "1.0"
    multiDexEnabled = true
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    isCoreLibraryDesugaringEnabled = true
  }
  kotlinOptions.jvmTarget = "1.8"
  buildTypes {
    named("release") {
      isMinifyEnabled = true
      setProguardFiles(listOf(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"))
    }
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