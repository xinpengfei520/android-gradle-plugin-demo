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
        create("plugin") {
            id = "com.github.xinpengfei520.XGradlePlugin"
            implementationClass = "com.xpf.android.gradle.plugin.XGradlePlugin"
        }
    }
}

/**
 * build sourcesJar
 */
val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier = "sources"
    from(sourceSets.main.get().allSource)
}

/**
 * Publish artifact to Maven Center
 */
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.xinpengfei520"
            artifactId = "XGradlePlugin"
            version = "1.0"
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
    repositories {
        maven {
            // change to point to your repo, e.g. http://my.org/repo
            url = uri("build/repo")
        }
    }
}