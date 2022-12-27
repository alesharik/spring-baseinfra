version = "1.0.1"

dependencies {
    api(project(":file-storage-api"))
    api(project(":common-minio"))
    implementation(libs.spring.starter.sentry)
    implementation(libs.spring.boot)
    implementation(libs.spring.web)
    implementation(libs.spring.boot.autoconfigure)
    implementation(libs.slf4j)
    implementation(libs.bundles.springdoc)
    testImplementation(libs.spring.starter.test)
}
