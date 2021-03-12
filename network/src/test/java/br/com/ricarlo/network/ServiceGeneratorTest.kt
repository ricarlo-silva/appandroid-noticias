package br.com.ricarlo.network

import br.com.ricarlo.network.di.NetworkModule
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.check.checkModules
import org.koin.test.inject
import retrofit2.HttpException
import retrofit2.http.GET

@RunWith(JUnit4::class)
class ServiceGeneratorTest : KoinTest {

    interface WebService {
        @GET("news")
        suspend fun get(): ApiResponse<List<String>>
    }

    private val service: WebService by inject()

    class NetworkConfigTest : INetworkConfig {
        override fun baseUrl(): String {
            return "https://4bd65aae-3330-4bd4-9f0e-6346414318bb.mock.pstmn.io"
        }

        override fun connectTimeout(): Long {
            return 30
        }

        override fun readTimeout(): Long {
            return 30
        }

        override fun isDebug(): Boolean {
            return true
        }
    }

    private val mockedAppModule = module(override = true) {
        single<INetworkConfig> {
            NetworkConfigTest()
        }

        single<WebService> {
            Service(get()).createService(WebService::class.java)
        }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.ERROR)
        modules(mockedAppModule + NetworkModule.module)
    }

    @Test
    fun `check modules`() {
        checkModules {
            modules(mockedAppModule + NetworkModule.module)
        }
    }

//    @Test
//    fun test1() {
//        runBlocking {
//            val response = service.get()
//            Assert.assertEquals(listOf("1", "2"), response.data)
//        }
//    }

    @Test
    fun test2() {
        runBlocking {

            runCatching { service.get() }
                .onFailure {
                    Assert.assertTrue(it is HttpException)
                }
        }
    }
}
