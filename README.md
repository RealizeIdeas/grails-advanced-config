# Grail Advanced Config Plugin

A small Gradle plugin built to help with Grails config files.

It takes config files from different locations and composes it into **application.yml**

![Config structure](https://github.com/RealizeIdeas/grails-advanced-config/blob/master/src/docs/img/config_structure.png)

## Setup

Basic setup in **build.gradle**:

```groovy
buildscript {
    repositories {
        maven { url "https://dl.bintray.com/reid/plugins" }
    }
    dependencies {
        classpath 'net.realizeideas:grails-advanced-config:1.0'
    }
}
apply plugin: "net.realizeideas.grails-advanced-config"
```
By default plugin takes all input config files from: 
- grails-app/conf/configurations/common 
- grails-app/conf/configurations/"${grails.env}"

You can override this behavior and set required config files manually:

```groovy
advancedConfig {
   configFiles = files(
        'grails-app/conf/config1.yml',
        'grails-app/conf/config2.yml',
   ) as List
}
```
## Supported file format

For input files:
- yml
- groovy
- json
- xml

Output file - **application.yml**
