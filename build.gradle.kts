// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Libs.Gradle.plugin)
        classpath(kotlin("gradle-plugin", version = Libs.Kotlin.version))
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://dl.bintray.com/pushe/preview")
        maven(url = "https://developer.huawei.com/repo")
    }
}

tasks.register("clean", Delete::class) {
   delete(rootProject.buildDir)
}

tasks.register("mamad") {
    // do some stuff
}