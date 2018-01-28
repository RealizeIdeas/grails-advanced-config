package net.realizeideas.grails.advanced.config

import groovy.json.JsonSlurper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.yaml.snakeyaml.Yaml

/**
 * Created by Evgeny Smirnov <illerax@gmail.com>
 */

class BuildGrailsConfigFileTask extends DefaultTask {

    @TaskAction
    void build() {
        BuildGrailsConfigFileExtension configExt = project.extensions.findByType(BuildGrailsConfigFileExtension)
        def map = mergeConfigFiles(configExt.configFiles)
        writeApplicationYml(configExt.target, map)
    }

    void writeApplicationYml(File file, Map map) {
        file.write(new Yaml().dumpAsMap(map))
    }

    Map addYmlConfigFile(File file) {
        Map map = [:]
        new Yaml().loadAll(file.text).each {
            if (it instanceof Map) {
                map = BuildGrailsConfigUtil.merge(map, it as Map)
            }
        }
        map
    }

    Map addGroovyConfigFile(File file) {
        new ConfigSlurper().parse(file.text)
    }

    Map addJsonConfigFile(File file) {
        new JsonSlurper().parseText(file.text)
    }

    Map addXmlConfigFile(File file) {
        BuildGrailsConfigUtil.mapFromNode(
                new XmlParser().parseText(file.text)
        )
    }

    Map mergeConfigFiles(List<File> files) {
        def map = [:]
        files.each { File file ->
            logger.debug("Processing config file ${file.path}")
            if (file.path.endsWith('.yml')) {
                map = BuildGrailsConfigUtil.merge(map, addYmlConfigFile(file))
            } else if (file.path.endsWith('.groovy')) {
                map = BuildGrailsConfigUtil.merge(map, addGroovyConfigFile(file))
            } else if (file.path.endsWith('.json')) {
                map = BuildGrailsConfigUtil.merge(map, addJsonConfigFile(file))
            } else if (file.path.endsWith('.xml')) {
                map = BuildGrailsConfigUtil.merge(map, addXmlConfigFile(file))
            }
        }
        return map
    }
}
