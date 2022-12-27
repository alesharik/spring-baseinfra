package com.alesharik.country.provider

import com.fasterxml.jackson.core.type.TypeReference
import com.nplone.gatling.utils.JsonBodyDsl.bodyJson
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.ScenarioBuilder
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status

object CountryProviderScenario {
    val gotCountries = scenario("CountryProvider/Fetch countries")
        .exec(
            http("Fetch countries")
                .get("/api/v1/countries")
                .check(
                    status().shouldBe(200),
                    bodyJson(object : TypeReference<List<Country>> (){}).transform { it.size }.gt(1)
                )
        )
}

fun ScenarioBuilder.fetchCountries() = exec(CountryProviderScenario.gotCountries)
