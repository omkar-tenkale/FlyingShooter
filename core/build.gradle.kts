//listOf(implementation(Java, implementationTestJava)*.options*.encoding = "UTF-8")

dependencies {

  val gdxVersion = "1.11.0"
  val aiVersion = "1.8.2"
  val kryoVersion = "5.4.0"
  val guacamoleVersion = "0.3.2"
  val kryoNetVersion = "2.22.8"
  val screenManagerVersion = "0.6.8"
  val controllerScene2DVersion = "2.3.0"
  val ktxVersion = "1.11.0-rc5"
  val artemisOdbVersion = "2.3.0"
  val kotlinxCoroutinesVersion = "1.6.4"
  api("com.badlogicgames.gdx:gdx-ai:$aiVersion")
  api("com.badlogicgames.gdx:gdx-box2d:$gdxVersion")
  api("com.badlogicgames.gdx:gdx-freetype:$gdxVersion")
  api("com.badlogicgames.gdx:gdx:$gdxVersion")
  api("com.esotericsoftware:kryo:$kryoVersion")
  api("com.github.crykn.guacamole:core:$guacamoleVersion")
  api("com.github.crykn.guacamole:gdx:$guacamoleVersion")
  api("com.github.crykn:kryonet:$kryoNetVersion")
  api("com.github.crykn:libgdx-screenmanager:$screenManagerVersion")
  api("de.golfgl.gdxcontrollerutils:gdx-controllerutils-scene2d:$controllerScene2DVersion")
  api("io.github.libktx:ktx-actors:$ktxVersion")
  api("io.github.libktx:ktx-app:$ktxVersion")
  api("io.github.libktx:ktx-artemis:$ktxVersion")
  api("io.github.libktx:ktx-assets-async:$ktxVersion")
  api("io.github.libktx:ktx-assets:$ktxVersion")
  api("io.github.libktx:ktx-async:$ktxVersion")
  api("io.github.libktx:ktx-box2d:$ktxVersion")
  api("io.github.libktx:ktx-collections:$ktxVersion")
  api("io.github.libktx:ktx-freetype-async:$ktxVersion")
  api("io.github.libktx:ktx-freetype:$ktxVersion")
  api("io.github.libktx:ktx-graphics:$ktxVersion")
  api("io.github.libktx:ktx-i18n:$ktxVersion")
  api("io.github.libktx:ktx-json:$ktxVersion")
  api("io.github.libktx:ktx-log:$ktxVersion")
  api("io.github.libktx:ktx-style:$ktxVersion")
  api("io.github.libktx:ktx-tiled:$ktxVersion")
  api("net.onedaybeard.artemis:artemis-odb:$artemisOdbVersion")
  api(kotlin("stdlib"))
  api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
  api(project(":shared"))
}
