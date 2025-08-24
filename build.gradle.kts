plugins {
    id("java")
    id("io.qameta.allure") version "2.12.0"
    id("io.freefair.lombok") version "8.11"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation ("org.junit.jupiter:junit-jupiter")
    implementation ("io.qameta.allure:allure-junit-platform:2.27.0")
    implementation ("com.microsoft.playwright:playwright:1.48.0")
    implementation("com.github.automatedowl:allure-environment-writer:1.0.0")
    implementation ("org.aeonbits.owner:owner:1.0.12")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.test {
    useJUnitPlatform()
}