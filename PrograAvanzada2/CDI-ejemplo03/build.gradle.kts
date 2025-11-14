plugins {
    id("java")
    id("io.freefair.lombok") version "9.1.0"
}

group = "com.progavanzada"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // CDI (Weld SE)
    implementation("org.jboss.weld.se:weld-se-core:6.0.3.Final")

    // Opcional: Jandex (útil si haces scanning)
    implementation("io.smallrye:jandex:3.2.3")
    // https://mvnrepository.com/artifact/com.zaxxer/HikariCP
    implementation("com.zaxxer:HikariCP:7.0.2")
    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    implementation("org.projectlombok:lombok:1.18.42")
    // https://mvnrepository.com/artifact/io.helidon.dbclient/helidon-dbclient
    implementation("io.helidon.dbclient:helidon-dbclient:4.3.2")
    // https://mvnrepository.com/artifact/io.helidon.dbclient/helidon-dbclient-jdbc
    implementation("io.helidon.dbclient:helidon-dbclient-jdbc:4.3.2")
    // https://mvnrepository.com/artifact/io.helidon.dbclient/helidon-dbclient-jdbc
    implementation("io.helidon.dbclient:helidon-dbclient-hikari:4.3.2")
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.7.8")
    // https://mvnrepository.com/artifact/io.helidon.config/helidon-config-yaml
    implementation("io.helidon.config:helidon-config-yaml:4.3.2")


}
sourceSets{
    main{
        output.setResourcesDir(file("${buildDir}/classes/java/main"))
    }
}
tasks.test {
    useJUnitPlatform()
}