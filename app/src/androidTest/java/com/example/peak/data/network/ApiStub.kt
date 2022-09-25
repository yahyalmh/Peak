package com.example.peak.data.network

import com.example.peak.util.AssetUtil
import com.github.tomakehurst.wiremock.client.WireMock.*


/**
 * @author yaya (@yahyalmh)
 * @since 23th September 2022
 */
object ApiStub {
    fun stubRectanglesResponseWithError(errorCode: Int) {
        val url = "/rectangles"
        stubFor(get(urlPathMatching(url)).willReturn(aResponse().withStatus(errorCode)))
    }

    fun stubRectanglesResponse() {
        val url = "/rectangles"
        val responseFileName = "rectangles.json"

        val jsonBody: String = AssetUtil.readAssetFile(responseFileName)

        stubFor(
            get(urlPathMatching(url)).willReturn(
                aResponse().withStatus(200).withBody(jsonBody)
            )
        )
    }
}
