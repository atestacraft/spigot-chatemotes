import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.7.20"
}

group = "me.apyr"
version = "1.0"

repositories {
  mavenCentral()
  maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
  compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")

  // provided by server
  compileOnly("org.apache.httpcomponents:httpcore:4.4.14")

  // testImplementation(kotlin("test"))
}

/*tasks.test {
  useJUnitPlatform()
}*/

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "11"
}

val jar by tasks.getting(Jar::class) {
  archiveFileName.set("ChatEmotes-nostdlib.jar")
}

val fatJar: Jar = task("fatJar", type = Jar::class) {
  archiveFileName.set("ChatEmotes.jar")
  from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
    exclude("META-INF", "META-INF/**")
  }
  with(tasks.jar.get() as CopySpec)
}

tasks {
  "build" {
    dependsOn(fatJar)
  }
}
