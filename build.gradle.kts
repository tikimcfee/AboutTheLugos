import org.gradle.jvm.tasks.Jar

group = "lugo.devs"
version = "1.0"

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.serialization") version "1.4.10"
    
    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    jcenter()
    mavenCentral()
    maven { setUrl("https://kotlin.bintray.com/kotlin-js-wrappers/") }
}

dependencies {
    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib-jdk8"))
    
    // - Server -
    implementation("io.javalin:javalin:2.7.0")
    implementation("org.slf4j:slf4j-simple:1.7.25")
    
    // Serialization and networking glue
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    
    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    
    // - Time -
    implementation("joda-time:joda-time:2.10.1")
    
    // - Web Tools : CSS -
    implementation("org.jetbrains:kotlin-css:1.0.0-pre.141-kotlin-1.4.21")
}

application {
    // Define the main class for the CLI application.
    mainClassName = "visual_interfaces.web.javalinRouting.JavalinWebFrameworkWrapperKt"
}

val fatJar = task("fatJar", type = Jar::class) {
    archiveBaseName.set("${project.name}-fat")
    
    from(sourceSets.main.get().output)
    
    dependsOn(configurations.runtimeClasspath)
    from(configurations.runtimeClasspath.get()
        .filter { it.name.endsWith("jar") }
        .map { zipTree(it) }
    )
    
    exclude(
        "**/*.kotlin_metadata"
//        "**/*.kotlin_module",
//        "**/*.kotlin_builtins"
    )
    
    manifest {
        attributes["Main-Class"] = "visual_interfaces.web.javalinRouting.JavalinWebFrameworkWrapperKt"
    }
}
