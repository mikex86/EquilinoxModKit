import org.gradle.internal.os.OperatingSystem

plugins {
    java
}

group = "me.gommeantilegit"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val classifier = when (OperatingSystem.current()) {
    OperatingSystem.LINUX -> "natives-linux"
    OperatingSystem.MAC_OS -> "natives-macos"
    OperatingSystem.WINDOWS -> "natives-windows"
    else -> throw Error("Unrecognized or unsupported Operating system. Please set \"lwjglNatives\" manually")
}

dependencies {
    compile(files("lib/fernflower.jar"))
    compile(files("lib/cfr.jar"))
    compile("org.lwjgl.lwjgl", "lwjgl-platform", version = "2.9.3", classifier = classifier)
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}