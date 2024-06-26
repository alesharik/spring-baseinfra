version = "1.1.1"

dependencies {
    api(libs.minio)
    implementation(libs.spring.starter.sentry)
    implementation(libs.tika.core)
    implementation(libs.spring.boot)
    implementation(libs.spring.web)
    implementation(libs.spring.boot.autoconfigure)
    implementation(libs.okhttp) {
        isForce = true
    }
    testImplementation(libs.spring.starter.test)

    constraints {
        implementation(libs.okhttp)
    }
}
