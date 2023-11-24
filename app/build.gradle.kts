plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.producteurapp"
    compileSdk = 34
//    sourceSets {
//        named("main") {
//            java.srcDirs(
//                "/home/mathieu/Documents/Projet/Projet_HAI925I/ApiCommerce2/src/main/java"
//            )
//            kotlin.srcDirs(
//                "src/main/kotlin"
//            )
//        }
//    }
    defaultConfig {
        applicationId = "com.example.producteurapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    }
    buildFeatures {
        viewBinding = true
    }

}

val ktorVersion = "2.3.5"
dependencies {
    implementation ("io.ktor:ktor-client-core:$ktorVersion")
    implementation ("io.ktor:ktor-client-json:$ktorVersion")
    implementation ("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
//    implementation("org.projectlombok:lombok:1.18.22")
//    implementation("jakarta.platform:jakarta.jakartaee-api:9.0.0")

    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("com.android.support:cardview-v7:28.0.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}