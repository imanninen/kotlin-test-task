import org.jetbrains.academy.test.system.core.models.method.TestMethodInvokeData
import org.jetbrains.kotlin.test.task.tamagotchi.models.Command
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.LinkedList

class Tests {
    companion object {
        private val command2 = 2
        private val expectedCommandStorage2 = LinkedList(listOf(Command.Clean))
        private val command5 = 5
        private val expectedCommandStorage5 = LinkedList(listOf(Command.Sleep))
        private val command8 = 8
        private val expectedCommandStorage8 = LinkedList(listOf(Command.Eat))

        @JvmStatic
        fun addCommandMethodTestData() = listOf(
            // initial, expected
            Arguments.of(command2, expectedCommandStorage2),
            Arguments.of(command5, expectedCommandStorage5),
            Arguments.of(command8, expectedCommandStorage8),
        )
    }
    @Test // TODO(probably to complex)
    fun commandFieldTest() {
        val clazz = gameServiceTest.checkBaseDefinition()
        val instance = clazz.getConstructor().newInstance()
        val field = clazz.declaredFields.find { it.name == commandStorageTestVar.name }
            ?: error("No such field ${commandStorageTestVar.name}")
        field.isAccessible = true
        val currentValue = field.get(instance)
        val expected = expectedVariablesValues[field.name]
        assertEquals(expected, currentValue) {"Init value of ${field.name} should be $expected"}
    }

    @Test
    fun gameServiceTestClassTest() {
        val clazz = gameServiceTest.checkBaseDefinition()
        val instance = clazz.getConstructor().newInstance()
        gameServiceTest.checkFieldsDefinition(clazz, false)
        gameServiceCompanionTest.checkBaseDefinition()
        expectedVariablesValues.forEach { (name, value) ->
            val field = clazz.declaredFields.find {it.name == name} ?: error("No such field $name!")
            field.isAccessible = true
            val currentValue = field.get(instance)
            assertEquals(value, currentValue) {"Initial value of $name is wrong!"}
        }
    }
    @ParameterizedTest
    @MethodSource("addCommandMethodTestData")
    fun addCommandTestMethodTest(inputCommand: Int, expected: LinkedList<Command>) {
        val invokeData = TestMethodInvokeData(gameServiceTest, addCommandTestMethod)
        val methodReturn = gameServiceTest.invokeMethodWithArgs(
            args = arrayOf(inputCommand),
            invokeData = invokeData,
            isPrivate = false,
        )
        assertEquals(true, methodReturn) {"Try to call ${addCommandTestMethod.name} method on a $inputCommand"}
        val field = invokeData.clazz.declaredFields.find { it.name == commandStorageTestVar.name }
            ?: error("No such field ${commandStorageTestVar.name}!")
        field.isAccessible = true
        val currentValue = field.get(invokeData.instance)
        assertEquals(expected, currentValue) { "Try to call the ${addCommandTestMethod.name} on an $inputCommand" }
    }

    @Test
    fun getCommandTestMethodStackTest() {

    }

    @Test
    fun getCommandTestMethodQueueTest() {

    }

}