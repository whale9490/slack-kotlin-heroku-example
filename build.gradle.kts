import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.kotlin.dsl.provideDelegate
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.6.10"
    application
    id("com.github.johnrengelman.shadow") version "7.1.1"
}

group = "me.myname"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val slackSdkVersion = "1.16.0"
    implementation("com.slack.api:bolt:$slackSdkVersion")
    implementation("com.slack.api:bolt-jetty:$slackSdkVersion")
    implementation("com.slack.api:slack-api-model-kotlin-extension:$slackSdkVersion")
    implementation("com.slack.api:slack-api-client-kotlin-extension:$slackSdkVersion")

    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")

    implementation("ch.qos.logback:logback-classic:1.3.0-alpha12")
}

tasks {
    val compileKotlin by existing(KotlinCompile::class) {
        kotlinOptions.jvmTarget = "11"
    }

    val shadowJar by existing(ShadowJar::class) {
        archiveFileName.set("app.jar")
    }

    // For Heroku
    val stage by registering {
        dependsOn(shadowJar)
    }
}

application {
    mainClass.set("MainKt")
}
