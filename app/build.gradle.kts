plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Versions.Build.compileSdk)

    defaultConfig {
        applicationId = "ir.malv.pusheprofiler"
        minSdkVersion(Versions.Build.minSdk)
        targetSdkVersion(Versions.Build.targetSdk)
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.version
        kotlinCompilerVersion = "1.4.20"
    }
}

dependencies {
    implementation(kotlin("stdlib", version = Libs.Kotlin.version))
    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.material)
    implementation(Libs.AndroidX.lifecycle)
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Compose.tooling)
    implementation(Libs.AndroidX.Compose.runtimeLiveData)
    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.Test.ext)
    androidTestImplementation(Libs.Test.espresso)

    api("co.pushe.plus:base:2.4.1-beta05")
    implementation("io.reactivex.rxjava2:rxjava:2.2.20")
    implementation("ir.malv.utils:pulp:0.4.0")
    implementation("androidx.work:work-runtime-ktx:2.4.0")
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs.toMutableList()
            .also { it.addAll(listOf("-Xallow-jvm-ir-dependencies", "-Xskip-prerelease-check")) }
    }
}

//apply {
//    this.plugin("com.getkeepsafe.dexcount")
//}