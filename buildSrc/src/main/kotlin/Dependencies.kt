private object Versions {

    const val androidPlugin = "3.6.0"
    const val kotlin = "1.3.72"
    const val kotlinCoroutinesAndroid = "1.3.5"
    const val lifecycle_version = "2.2.0"
    const val ktLintVersion = "0.31.0"
    const val ktLintHtmlReporter = "0.1.2"
    const val koinAndroid = "1.0.2"
    const val googleServices = "4.3.0"
    const val graphql = "2.0.0"
    const val firebase = "17.0.0"
    const val shimmer = "0.4.0"

    const val appcompat = "1.0.0"
    const val androidSupport = "1.0.0-rc01"
    const val navigationVersion = "2.0.0"
    const val moshi = "1.8.0"
    const val okhttp3 = "3.14.2"
    const val retrofit2 = "2.6.0"
    const val room = "2.1.0"
    const val multidex = "2.0.1"
    const val constraintLayout = "2.0.0-beta2"
    const val jwtdecode = "2.0.0"
    const val gson = "2.8.6"

    const val androidxTest = "1.2.0"
    const val androidxFragment = "1.1.0-beta01"
    const val espressoCore = "3.1.1"
    const val mockitoInline = "3.0.0"
    const val archCoreTest = "1.1.1"
    const val androidJunit = "1.1.1"
    const val robolectric = "4.3"
}

object Libs {
    object Gradle {
        const val graphQl = "com.apollographql.apollo:apollo-gradle-plugin:${Versions.graphql}"
        const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidPlugin}"
        const val kotlinGradlePlugin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val googleServicesGradlePlugin =
            "com.google.gms:google-services:${Versions.googleServices}"
        const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationVersion}"
    }

    object Appcompat {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val kotlinCore = "androidx.core:core-ktx:${Versions.appcompat}"
        const val preferences = "androidx.preference:preference-ktx:${Versions.appcompat}"
    }

    object Support {
        const val design = "com.google.android.material:material:${Versions.androidSupport}"
        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.androidxFragment}"
    }

    object Test {
        const val runner = "androidx.test:runner:${Versions.androidxTest}"
        const val androidJunit = "androidx.test.ext:junit:${Versions.androidJunit}"
        const val core = "androidx.test:core:${Versions.androidxTest}"
        const val rules = "androidx.test:rules:${Versions.androidxTest}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
        const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoInline}"
        const val koin = "org.koin:koin-test:${Versions.koinAndroid}"
        const val fragment = "androidx.fragment:fragment-testing:${Versions.androidxFragment}"
        const val archCore = "android.arch.core:core-testing:${Versions.archCoreTest}"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutinesAndroid}"
        const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    }

    object ktLint {
        const val ktlint = "com.github.shyiko:ktlint:${Versions.ktLintVersion}"
        const val htmlReporter = "me.cassiano:ktlint-html-reporter:${Versions.ktLintHtmlReporter}"
    }

    object Network {
        const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
        const val loggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"
        const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"

        const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
        const val retrofit2ConverterMoshi =
            "com.squareup.retrofit2:converter-moshi:${Versions.retrofit2}"

        const val gson = "com.google.code.gson:gson:${Versions.gson}"
        const val jwtdecode = "com.auth0.android:jwtdecode:${Versions.jwtdecode}"
        const val graphQl = "com.apollographql.apollo:apollo-runtime:${Versions.graphql}"
        const val graphQlCoroutines = "com.apollographql.apollo:apollo-coroutines-support:${Versions.graphql}"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:${Versions.room}"
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
        const val ktx = "androidx.room:room-ktx:${Versions.room}"
    }

    object Koin {
        const val viewModel = "org.koin:koin-android-viewmodel:${Versions.koinAndroid}"
        const val android = "org.koin:koin-android:${Versions.koinAndroid}"
    }

    object UI {
        const val navigationUi =
            "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
        const val constraintLayout =
            "com.android.support.constraint:constraint-layout:${Versions.constraintLayout}"
        const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"
        const val databinding = "com.android.databinding:compiler:${Versions.androidPlugin}"
    }

    object Coroutines {
        const val kotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutinesAndroid}"
        const val kotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutinesAndroid}"

        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
        const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_version}"
    }


    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val firebase = "com.google.firebase:firebase-core:${Versions.firebase}"
    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"
}