buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(Libs.Gradle.androidGradlePlugin)
        classpath(Libs.Gradle.kotlinGradlePlugin)
        classpath(Libs.Gradle.googleServicesGradlePlugin)
        classpath(Libs.Gradle.graphQl)
        classpath(Libs.Gradle.safeArgs)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

