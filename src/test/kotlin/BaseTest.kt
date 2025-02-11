import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream
import kotlin.test.assertEquals

open class BaseTest(val workDir: String) {
    private lateinit var outputStream: ByteArrayOutputStream
    private lateinit var inputStream: InputStream

    @BeforeEach
    fun set() {
        outputStream = ByteArrayOutputStream()
        val ps = PrintStream(outputStream)
        System.setOut(ps)
        inputStream = this::class.java.getResource("$workDir/input.txt")!!.openStream()
        System.setIn(inputStream)
    }

    fun checkOutput(transformer: (String) -> String = { it }) {
        val resultString = outputStream.toString("UTF-8").transformEachLine(transformer)
        val expectedOutPut = this::class.java.getResource("$workDir/output.txt")!!.readText().transformEachLine(transformer)
        assertEquals(expectedOutPut, resultString.trim(), "Not equals")
    }

    fun String.transformEachLine(transformer: (String) -> String) =
        this.lines().joinToString(separator = "\n", transform = transformer)

    fun sortByChars(str: String) = str.toCharArray().let {
        it.sort()
        String(it)
    }


}