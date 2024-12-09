plugins {
    kotlin("jvm") version "2.0.21" // Usa la última versión de Kotlin
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Dependencias necesarias para JavaFX
    implementation("org.openjfx:javafx-controls:23.0.1")
    implementation("org.openjfx:javafx-fxml:23.0.1")

    // Dependencia para Gson (opcional si trabajas con JSON)
    implementation("com.google.code.gson:gson:2.10.1")
}

application {
    // Clase principal del programa
    mainClass.set("com.example.MainKt")
}

javafx {
    version = "23.0.1"
    modules = listOf("javafx.controls", "javafx.fxml")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21) // Configura el proyecto para usar JDK 21
}

tasks.withType<JavaExec> {
    jvmArgs("--add-opens", "java.base/java.time=ALL-UNNAMED")
    jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
    jvmArgs("--add-opens", "java.base/java.util=ALL-UNNAMED")
}

