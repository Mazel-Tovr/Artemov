plugins {
    kotlin("jvm") version "1.4.31"
    application
}

group = "com.mazeltov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClassName = "MainKt"
}

java {
    modularity.inferModulePath.set(true)
}

tasks.withType<Jar> {
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
}
