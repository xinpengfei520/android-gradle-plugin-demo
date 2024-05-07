plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
}

dependencies {
    //compileOnly(gradleApi())
    //compileOnly(libs.plugins.jetbrains.kotlin.android)
    //compileOnly(kotlin("stdlib"))
    compileOnly(libs.gradle)
}

gradlePlugin {
    plugins {
        create("XGradle") {
            id = "com.github.xinpengfei520.XGradlePlugin"
            implementationClass = "com.xpf.android.gradle.plugin.XGradlePlugin"
        }
    }
}

group = "com.github.xinpengfei520"
version = "1.0"

java {
    withJavadocJar()
    withSourcesJar()
}

/**
 * Publish artifact to Maven Center
 */
publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "XGradle"
            from(components["java"])
            pom {
                name = "My XGradle Plugin"
                description = "A gradle plugin for android project"
                url = "https://github.com/xinpengfei520/android-gradle-plugin-demo"
                properties = mapOf(
                    "myProp" to "value",
                    "prop.with.dots" to "anotherValue"
                )
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "Vance"
                        name = "Vance Xin"
                        email = "xinpengfei520@gmail.com"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com:xinpengfei520/android-gradle-plugin-demo.git"
                    developerConnection = "scm:git:ssh://github.com:xinpengfei520/android-gradle-plugin-demo.git"
                    url = "https://github.com/xinpengfei520/android-gradle-plugin-demo"
                }
            }
        }
    }
    repositories {
        maven {
            // change to point to your repo, e.g. http://my.org/repo
            url = uri(layout.buildDirectory.dir("repo"))
//            val releasesRepoUrl = uri(layout.buildDirectory.dir("repos/releases"))
//            val snapshotsRepoUrl = uri(layout.buildDirectory.dir("repos/snapshots"))
//            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        }
    }
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}