package steps

import groovyx.net.http.HttpResponseDecorator

/**
 * Custom world for sharing objects
 */
class CustomWorld {
    Map request = [:]
    HttpResponseDecorator response = null
}
