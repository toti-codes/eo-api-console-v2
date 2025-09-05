plugins {
    java
    id("io.quarkus")
    id("com.diffplug.spotless") version "6.25.0"
    id("checkstyle")
    id("jacoco")
}

spotless {
    java {
        eclipse().configFile("${rootDir}/.config/codestyle/eclipse-java-google-style.xml")
        targetExclude("build/**", "**/quarkus-generated-sources/**")
    }
}

checkstyle {
    toolVersion = "10.20.0"
    configFile = file("${rootDir}/.config/checkstyle/checkstyle.xml")
    configDirectory = file("${rootDir}/.config/checkstyle")
    isIgnoreFailures = false
}

jacoco {
    toolVersion = "0.8.11"
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation("io.quarkiverse.logging.logback:quarkus-logging-logback:1.1.2")
    implementation("io.quarkiverse.reactivemessaging.nats-jetstream:quarkus-messaging-nats-jetstream:3.25.0")

    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-mutiny")
    implementation("io.quarkus:quarkus-hibernate-reactive-panache")
    implementation("io.quarkus:quarkus-reactive-pg-client")
    implementation("io.quarkus:quarkus-arc")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.quarkus:quarkus-junit5-mockito")
    testImplementation("io.rest-assured:rest-assured")
    // Lombok dependencies
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")

    runtimeOnly("net.logstash.logback:logstash-logback-encoder:6.6")
}

group = "com.doous.emite"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<Checkstyle> {
    reports {
        xml.required.set(false)
        html.required.set(true)
    }

    // Excluir archivos autogenerados
    exclude {
        it.file.path.contains("build") ||
                it.file.path.contains("target") ||
                it.file.path.contains("quarkus-generated-sources") ||
                it.file.path.contains("generated-sources") ||
                it.file.path.contains("generated-test-sources") ||
                it.file.path.contains("META-INF") ||
                it.name == "module-info.java"
    }
}

tasks.jacocoTestReport {
    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude("com/doous/emite/apiconsole/nats/domain/repository")
                exclude("com/doous/emite/apiconsole/shared/domain/repository")
            }
        })
    )
}

/*tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}*/
