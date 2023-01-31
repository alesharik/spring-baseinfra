version = "1.0.3"

dependencies {
    api(project(":file-storage-api"))
    api(project(":common-minio"))
    implementation(libs.spring.starter.sentry)
    implementation(libs.spring.boot)
    implementation(libs.spring.web)
    implementation(libs.spring.boot.autoconfigure)
    implementation(libs.slf4j)
    implementation(libs.problems.web)
    testImplementation(libs.spring.starter.test)
}
