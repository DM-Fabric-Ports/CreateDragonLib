# Depend on it
```gradle
repositories {
    maven {
        // Maven of jitpack.io
        url "https://jitpack.io/"
    }
}

dependencies {
    modImplementation include("com.github.DM-Fabric-Ports:CreateDragonLib:${project.dragon_lib_version}")
}
```