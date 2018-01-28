import net.realizeideas.grails.advanced.config.BuildGrailsConfigFileTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by Evgeny Smirnov <illerax@gmail.com>
 */
class BuildGrailsConfigFileTaskSpec extends Specification {

    final tempFolder = File.createTempDir()

    def setup() {

        new File("$tempFolder/test1.yml") << '''
file1: added
grails:
  advanced:
    config: works fine 
'''
        new File("$tempFolder/test2.yml") << '''
file2: added
grails:
  advanced:
    config: is not working
    yml: .yml 
'''
        new File("$tempFolder/test3.groovy") << '''
file3 = "added"
grails{
  advanced{
    groovy=".groovy"
  }
} 
'''
        new File("$tempFolder/test4.json") << '''
{
  "file4": "added",
  "grails": {
    "advanced": {
      "json": ".json"
    }
  }
} 
'''
        new File("$tempFolder/test5.xml") << '''
<config>
  <file5>added</file5>
  <grails>
    <advanced>
      <xml>.xml</xml>
    </advanced>
  </grails>
</config>
'''
    }

    void 'Test merge config files order'() {
        given:
        Project project = ProjectBuilder.builder().build()
        BuildGrailsConfigFileTask task = project.task('buildGrailsConfigFileTask', type: BuildGrailsConfigFileTask)
        expect:
        task.mergeConfigFiles([
                new File("$tempFolder/test2.yml"),
                new File("$tempFolder/test1.yml"),
                new File("$tempFolder/test3.groovy"),
                new File("$tempFolder/test4.json"),
                new File("$tempFolder/test5.xml")
        ]) == [
                file1 : 'added',
                file2 : 'added',
                file3 : 'added',
                file4 : 'added',
                file5 : 'added',
                grails: [
                        advanced: [
                                config: "works fine",
                                yml   : ".yml",
                                groovy: ".groovy",
                                json: ".json",
                                xml: ".xml"
                        ]
                ]
        ]
    }

}
