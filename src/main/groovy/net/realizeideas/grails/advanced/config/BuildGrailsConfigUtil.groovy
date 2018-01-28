package net.realizeideas.grails.advanced.config


/**
 * Created by Evgeny Smirnov <illerax@gmail.com>
 */
class BuildGrailsConfigUtil {

    static Map merge(Map[] sources) {
        if (sources.length == 0) return [:]
        if (sources.length == 1) return sources[0]
        sources.inject([:]) { result, source ->
            source.each { k, v ->
                result[k] = result[k] instanceof Map ? merge(result[k], v) : v
            }
            result
        }
    }

    static def mapFromNode(Node node){
        node.children().inject ([:])  { map, childNode ->
            if (childNode instanceof Node) {
                map + [(childNode.name()): mapFromNode(childNode)]
            }  else {
                childNode.toString()
            }
        }
    }
}
