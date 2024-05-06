plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
}

dependencies {
    //compileOnly(gradleApi())
    //compileOnly(libs.plugins.jetbrains.kotlin.android)
    compileOnly(libs.gradle)
}

gradlePlugin {
    plugins {
        create("plugin") {
            id = "com.github.xinpengfei520.XGradlePlugin"
            implementationClass = "XGradlePlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.xinpengfei520"
            artifactId = "XGradlePlugin"
            version = "1.0"
            from(components["java"])
        }
    }
}