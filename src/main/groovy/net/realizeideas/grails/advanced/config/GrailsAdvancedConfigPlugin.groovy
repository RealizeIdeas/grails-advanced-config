package net.realizeideas.grails.advanced.config

import groovy.transform.CompileStatic
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.GroovyCompile

/**
 * Created by Evgeny Smirnov <illerax@gmail.com>
 */
@CompileStatic
class GrailsAdvancedConfigPlugin implements Plugin<Project> {

    void apply(Project project) {
        registerBuildGrailsConfigFileExtension(project)
        registerBuildGrailsConfigFileTask(project)
    }

    protected void registerBuildGrailsConfigFileTask(Project project) {
        def buildGrailsConfigFileTask = project.tasks.create(
                name: "buildGrailsConfigFileTask",
                type: BuildGrailsConfigFileTask,
                overwrite: true)
        project.tasks.withType(GroovyCompile) { GroovyCompile task ->
            task.dependsOn buildGrailsConfigFileTask
        }
    }

    protected void registerBuildGrailsConfigFileExtension(Project project) {
        project.extensions.create("advancedConfig", BuildGrailsConfigFileExtension, project)
    }

}