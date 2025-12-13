import org.junit.jupiter.api.Test
import org.mohaberabi.testideplugin.com.mohaberabi.models.fakes.fakeAnotherClass
import org.mohaberabi.testideplugin.com.mohaberabi.models.fakes.fakeMyClass
import kotlin.test.assertEquals

class TestingFakes {


    @Test
    fun `test me `() {
        assert(true)

        val fake = fakeMyClass {
            map2 = mapOf(fakeAnotherClass() to fakeAnotherClass())
        }
        assertEquals(fake.map2, mapOf(fakeAnotherClass() to fakeAnotherClass()))
        fakeAnotherClass {

        }
    }

}