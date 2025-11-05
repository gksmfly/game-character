plugins {
    kotlin("jvm") version "2.2.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    // JDK 24 사용
    jvmToolchain(24)
}

application {
    // 패키지가 rts 라면 이게 정답 (Main.kt의 package rts 기준)
    mainClass.set("rts.MainKt")
}

tasks.test {
    useJUnitPlatform()
}
