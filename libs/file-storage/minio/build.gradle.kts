version = "1.0.2"

dependencies {
    api(project(":file-storage-api"))
    api(project(":common-minio"))
    implementation(libs.spring.starter.sentry)
    implementation(libs.spring.boot)
    implementation(libs.spring.web)
    implementation(libs.spring.boot.autoconfigure)
    implementation(libs.slf4j)
    testImplementation(libs.spring.starter.test)
}
