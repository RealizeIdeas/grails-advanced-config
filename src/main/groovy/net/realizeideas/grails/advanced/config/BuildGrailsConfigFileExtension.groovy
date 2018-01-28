package net.realizeideas.grails.advanced.config

import org.gradle.api.Project

/**
 * Created by Evgeny Smirnov <illerax@gmail.com>
 */
class BuildGrailsConfigFileExtension {

    final static DEFAULT_CONFIG_PATH = 'grails-app/conf/configurations'
    final static DEFAULT_TARGET = 'grails-app/conf/application.yml'

    Project project
    List<File> configFiles
    File target = new File("$DEFAULT_TARGET")
    Map finalProps = [:]

    BuildGrailsConfigFileExtension(Project project) {
        this.project = project
        this.configFiles = defaultConfigFiles()
    }

    List<File> defaultConfigFiles() {
        List<File> defaultFiles = []
        // Add common files for all environments
        defaultFiles.addAll(project.fileTree(DEFAULT_CONFIG_PATH) {
            include "**/common/*.yml"
        }.files)
        // Add environment based files
        defaultFiles.addAll(project?.fileTree(DEFAULT_CONFIG_PATH) {
            include "**/${envName()}/*.yml"
        }.files)
        return defaultFiles
    }

    static String envName() {
        System.getProperty('grails.env') ?: 'development'
    }
}
