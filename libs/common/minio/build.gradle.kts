version = "1.0.0"

dependencies {
    api(libs.minio)
    implementation(libs.spring.starter.sentry)
    implementation(libs.tika.core)
    implementation(libs.spring.boot)
    implementation(libs.spring.boot.autoconfigure)
    implementation(libs.bundles.springdoc)
    implementation(libs.problems.web)
    implementation(libs.okhttp) {
        isForce = true
    }
    testImplementation(libs.spring.starter.test)

    constraints {
        implementation(libs.okhttp)
    }
}
