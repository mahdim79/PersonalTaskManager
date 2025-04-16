plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

dependencies {

    implementation(libs.kotlinx.coroutine.android)

    // Region Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // Region Gson
    implementation(libs.gson)

}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
