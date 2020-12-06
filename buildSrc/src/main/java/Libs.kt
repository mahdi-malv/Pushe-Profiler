@Suppress("unused", "SpellCheckingInspection")
object Libs {

    // region global
    object Kotlin {
        const val version = "1.4.20"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1"
    }

    object Gradle {
        const val plugin = "com.android.tools.build:gradle:4.2.0-beta01"
    }
    // endregion

    // region Libraries
    object Maven {
        const val jitPack = "https://jitpack.io"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.3.2"
        const val multiDex = "androidx.multidex:multidex:2.0.1"
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val material = "com.google.android.material:material:1.2.1"
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-beta01"
        const val cardView = "androidx.cardview:cardview:1.0.0"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
        const val media = "androidx.media:media:1.1.0"
        const val exIfInterface = "androidx.exifinterface:exifinterface:1.2.0"
        const val vectorDrawable = "androidx.vectordrawable:vectordrawable-animated:1.1.0"

        object Navigation {
            const val compose = "androidx.navigation:navigation-compose:1.0.0-alpha02"
        }

        object Hilt {
            const val android = "com.google.dagger:hilt-android:2.28-alpha"
            const val lifeCycle = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02"
            const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:2.28-alpha"
            const val hiltCompiler = "androidx.hilt:hilt-compiler:1.0.0-alpha02"
            const val gradle = "com.google.dagger:hilt-android-gradle-plugin:2.28-alpha"
        }

        object Compose {
            const val version = "1.0.0-alpha08"
            const val ui = "androidx.compose.ui:ui:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val material = "androidx.compose.material:material:$version"
            const val runtimeLiveData = "androidx.compose.runtime:runtime-livedata:$version"
            const val animation = "androidx.compose.animation:animation:$version"
            const val iconExt = "androidx.compose.material:material-icons-extended:$version"

            object Ext {
                const val coilImage = "com.github.luca992:coil-composable:0.3.3"
            }
        }

        object DataStore {
            const val typed = "androidx.datastore:datastore:1.0.0-alpha03"
            const val preferences = "androidx.datastore:datastore-preferences:1.0.0-alpha03"
            const val protobuf = "com.google.protobuf:protobuf-javalite:3.10.0"
            const val gradle = "com.google.protobuf:gradle-plugin:0.8.12"
        }

        object Test {
            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
            const val junitExt = "androidx.test.ext:junit:1.1.1"
        }
    }

    object Utils {
        const val markdown = "com.github.mukeshsolanki:MarkdownView-Android:1.0.6"
        const val jMustache = "com.samskivert:jmustache:1.15"
    }

    object Google {
        const val material = "com.google.android.material:material:1.2.0"

        object Firebase {
            const val core = "com.google.firebase:firebase-core:17.5.0"
            const val iid = "com.google.firebase:firebase-iid:20.2.4"
            const val messaging = "com.google.firebase:firebase-messaging:20.2.4"
            const val analytics = "com.google.firebase:firebase-analytics-ktx:17.5.0"
            const val perf = "com.google.firebase:firebase-perf:19.0.8"
        }
    }

    object Network {
        object Retrofit {
            const val retrofit = "com.squareup.retrofit2:retrofit:2.6.4"
            const val rxJava2 = "com.squareup.retrofit2:adapter-rxjava2:2.5.0"
            const val converterGson = "com.squareup.retrofit2:converter-gson:2.6.4"
            const val converterMoshi = "com.squareup.retrofit2:converter-moshi:2.6.4"
            const val converterScalars = "com.squareup.retrofit2:converter-scalars:2.5.0"
        }

        object OkHttp {
            const val okHttp = "com.squareup.okhttp3:okhttp:4.9.0"
            const val mockServer = "com.squareup.okhttp3:mockwebserver::4.9.0"
            const val logging = "com.squareup.okhttp3:logging-interceptor:4.9.0"
        }

        object Moshi {
            const val moshi = "com.squareup.moshi:moshi:1.11.0"
            const val codeGen = "com.squareup.moshi:moshi-kotlin-codegen:1.11.0"
        }


    }

    object Image {
        const val coil = "io.coil-kt:coil:1.0.0"
        const val accompanist_coil = "dev.chrisbanes.accompanist:accompanist-coil:0.3.3.1"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:2.2.5"
        const val rxJava2 = "androidx.room:room-rxjava2:2.2.5"
        const val ktx = "androidx.room:room-ktx:2.2.5"
        const val compiler = "androidx.room:room-compiler:2.2.5"
    }

    object WorkManager {
        const val runtime = "androidx.work:work-runtime-ktx:2.4.0"
        const val rxJava2 = "androidx.work:work-rxjava2:2.4.0"
    }

    object Font {
        const val calligraphy3 = "io.github.inflationx:calligraphy3:3.1.1"
        const val viewPump = "io.github.inflationx:viewpump:2.0.3"
    }

    object Glide {
        const val core = "com.github.bumptech.glide:glide:4.11.0"
        const val coreCompiler = "com.github.bumptech.glide:compiler:4.9.0"
    }


    object Rx {
        const val rxJava2 = "io.reactivex.rxjava2:rxjava:2.2.19"
        const val rxAndroid2 = "io.reactivex.rxjava2:rxandroid:2.1.1"
    }

    object Logging {
        const val pulp = "ir.malv.utils:pulp:0.2.0"
    }

    object Test {
        const val junit = "junit:junit:4.13.1"
        const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
        const val ext = "androidx.test.ext:junit:1.1.2"
    }
    // endregion

}