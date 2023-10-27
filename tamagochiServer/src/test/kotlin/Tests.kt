import org.jetbrains.academy.test.system.core.models.method.TestMethodInvokeData
import org.jetbrains.kotlin.test.task.tamagotchi.game.GameService
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
        private val expectedCommandStorage2 = Command.Clean
        private val command5 = 5
        private val expectedCommandStorage5 = Command.Sleep
        private val command8 = 8
        private val expectedCommandStorage8 = Command.Eat
        private const val queue = "\"Queue\""
        private const val stack = "\"Stack\""

        @JvmStatic
        fun addCommandMethodTestData() = listOf(
            // initial, expected
            Arguments.of(command2, LinkedList(listOf(expectedCommandStorage2))),
            Arguments.of(command5, LinkedList(listOf(expectedCommandStorage5))),
            Arguments.of(command8, LinkedList(listOf(expectedCommandStorage8))),
        )
        @JvmStatic
        fun getCommandMethodTestData() = listOf(
            // mode, testCommandStorage, expectedReturn, expectedCommandStorage
            Arguments.of(queue, LinkedList(listOf(Command.Clean, Command.Play, Command.Eat)), Command.Clean,
                LinkedList(listOf(Command.Play, Command.Eat))),
            Arguments.of(stack, LinkedList(listOf(Command.Clean, Command.Play, Command.Eat)), Command.Eat,
                LinkedList(listOf(Command.Clean, Command.Play))),
            Arguments.of(stack, LinkedList(listOf(Command.Clean)), Command.Clean, LinkedList<Command>()),
            Arguments.of(queue, LinkedList(listOf(Command.Clean)), Command.Clean, LinkedList<Command>()),
//            Arguments.of(stack, LinkedList<Command>(), null, LinkedList<Command>()),
//            Arguments.of(queue, LinkedList<Command>(), null, LinkedList<Command>())
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
        assertEquals(expected, currentValue) { "Try to call the ${addCommandTestMethod.name} method on a $inputCommand" }
    }

    @Test
    fun getCommandTestMethodStackTest() {
        val service = GameService()
        var result = service.getCommand("\"Stack\"")
        assertEquals(null, result) {"Try to call getCommand method on an empty commandStorage with stack mode"}
        service.addCommand(1)
        service.addCommand(2)
        service.addCommand(3)
        result = service.getCommand("\"Stack\"")
        assertEquals(Command.Play, result) {"Something went wrong in getCommandFunction! Should be $result"}
        assertEquals(LinkedList(listOf(Command.Sleep, Command.Clean)), service.copyOfCommandStorage)
        {"\"Something went wrong in getCommand method!"}
    }

    @Test
    fun getCommandTestMethodQueueTest() {
        val service = GameService()
        var result = service.getCommand("\"Queue\"")
        assertEquals(null, result) {"Try to call getCommand method on an empty commandStorage with stack mode"}
        service.addCommand(1)
        service.addCommand(2)
        service.addCommand(3)
        result = service.getCommand("\"Queue\"")
        assertEquals(Command.Sleep, result) {"Something went wrong in getCommandFunction! Should be $result"}
        assertEquals(LinkedList(listOf(Command.Clean, Command.Play)), service.copyOfCommandStorage)
        {"\"Something went wrong in getCommand method!"}
    }

    /*
    * I thought that previous tests are not that good, so I decided and implemented this one.
    * But it has one problem and I don't know how to fix it: this test throws strange exception when it gets null from
    * invokeMethodWithArgs() despite that I specify my method as nullable.
    * Anyway I enjoyed completing this task, so thank you. I wish I get a job :))
    */
    @ParameterizedTest
    @MethodSource("getCommandMethodTestData")
    fun getMethodTestMain(mode: String, testCommandStorage: LinkedList<Command>,
                          expectedReturn: Command?, expectedCommandStorage: LinkedList<Command>) {
        val invokeData = TestMethodInvokeData(gameServiceTest, getCommandTestMethod)
        var field = invokeData.clazz.declaredFields.find { it.name == commandStorageTestVar.name }
            ?: error("No such field! ${commandStorageTestVar.name}")
        field.isAccessible = true
        field.set(invokeData.instance, testCommandStorage)

        val methodReturn = gameServiceTest.invokeMethodWithArgs(
            args = arrayOf(mode),
            invokeData = invokeData,
            isPrivate = false,
        )
        assertEquals(expectedReturn, methodReturn) {"Try to call ${addCommandTestMethod.name} method on a $mode"}
        field = invokeData.clazz.declaredFields.find { it.name == commandStorageTestVar.name }
            ?: error("No such field ${commandStorageTestVar.name}")
        field.isAccessible = true
        val currentStorage = field.get(invokeData.instance)
        assertEquals(expectedCommandStorage, currentStorage) {"You are not right!"}
    }
}