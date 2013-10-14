package steps

import cucumber.api.Scenario
import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks
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
    HttpBin.reset()
}

When(~'^I call delete$') {->
    response = HttpBin.delete()
}

Then(~'^the response should be successful$') {->
    assert response.status == 200
}

When(~'^I call put$') {->
    response = HttpBin.put([Something: "Something"])
}

When(~'^I call post$') {->
    response = HttpBin.post([Something: "Something"])
}
When(~'^I call get$') {->
    response = HttpBin.get([Something: "Something"])
}

/*After() { Scenario sc ->
    sc.write("This is the after block of the scenario")
}*/