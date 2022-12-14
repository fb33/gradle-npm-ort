import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "org.fbarbe"
version = File("$projectDir/version.txt").readText().trim()
java.sourceCompatibility = JavaVersion.VERSION_11

plugins {
    val kotlinVersion = "1.6.10"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion

    id("org.springframework.boot") version "2.5.9"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    jacoco
}

jacoco {
    toolVersion = "0.8.7"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-undertow") {
        exclude(group = "io.undertow", module = "undertow-websockets-jsr")
    }
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")

    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")
    implementation("com.lectra:koson:1.2.3")
    implementation("de.bwaldvogel:mongo-java-server:1.39.0")
    runtimeOnly("ch.qos.logback.contrib:logback-json-classic:0.1.5")
    runtimeOnly("ch.qos.logback.contrib:logback-jackson:0.1.5")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testRuntimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    testRuntimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    getByName<Jar>("jar") {
        enabled = false
    }

    withType<JacocoReport> {
        reports {
            xml.required.set(true)
        }
    }
}
