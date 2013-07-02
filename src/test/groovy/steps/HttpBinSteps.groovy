package steps

import cucumber.api.Scenario
import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks
import cucumber.api.PendingException
import httpbin.HttpBin

/**
 * Steps for testing HTTPBin client
 */
this.metaClass.mixin Hooks
this.metaClass.mixin EN


World {
    new CustomWorld()
}

Before {
    request.httpBin = new HttpBin()
}

When(~'^I call delete$') {->
    response = request.httpBin.delete()
}

Then(~'^the response should be successful$') {->
    assert response.status == 200
}

After() { Scenario sc ->
    sc.write("This is the after block of the scenario")
}