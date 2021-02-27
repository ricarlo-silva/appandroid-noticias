package br.com.ricarlo.network

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class JsonUtilsTest {

    @Test
    fun fromJson() {
        val json = "api-error.json".readFileFromAssetsOrResources()

        val response = fromJson<ApiErrorResponse>(json)

        Assert.assertTrue(response?.message == "Erro na request")
    }

    @Test
    fun toJson() {
        val response = ApiErrorResponse(message = "Not found")

        val json = response.toJson()

        Assert.assertEquals("{\"message\":\"Not found\"}", json)
    }

    @Test
    fun fromJsonList() {
        val json = "[\"one\",\"two\",\"three\"]"

        val response = fromJson<List<String>>(json)

        Assert.assertEquals(listOf("one", "two", "three"), response)
    }

    @Test
    fun listToJson() {
        val response = listOf("one", "two", "three")

        val json = response.toJson()

        Assert.assertEquals("[\"one\",\"two\",\"three\"]", json)
    }
}
