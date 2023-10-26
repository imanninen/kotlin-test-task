import org.jetbrains.academy.test.system.core.models.TestKotlinType
import org.jetbrains.academy.test.system.core.models.Visibility
import org.jetbrains.academy.test.system.core.models.classes.TestClass
import org.jetbrains.academy.test.system.core.models.method.TestMethod
import org.jetbrains.academy.test.system.core.models.variable.TestVariable
import org.jetbrains.academy.test.system.core.models.variable.VariableMutability
import org.jetbrains.kotlin.test.task.tamagotchi.models.Command
import java.util.LinkedList

internal val maxCapacityTestVar = TestVariable(
    name = "MAX_CAPACITY",
    javaType = "Int",
    visibility = Visibility.PRIVATE,
    mutability = VariableMutability.VAL,
    isInPrimaryConstructor = false,
    isConst = true
)

internal val commandStorageTestVar = TestVariable(
    name = "commandStorage",
    javaType = "LinkedList",
    visibility = Visibility.PRIVATE,
    mutability = VariableMutability.VAL,
    isInPrimaryConstructor = false,
)

internal val gameServiceTest = TestClass(
    "GameService",
    classPackage = "org.jetbrains.kotlin.test.task.tamagotchi.game",
    customMethods = listOf(
    // TODO("watch how it implemented in another course!")
    ),
    declaredFields = listOf(
        commandStorageTestVar,
        maxCapacityTestVar,
    )
)

internal val expectedVariablesValues = mapOf("commandStorage" to LinkedList<Command>(), "MAX_CAPACITY" to 16)



internal val gameServiceCompanionTest = TestClass(
    "GameService\$Companion",
    "org.jetbrains.kotlin.test.task.tamagotchi.game",
)

internal val addCommandTestMethod = TestMethod(
    name = "addCommand",
    returnType = TestKotlinType("Boolean"),
    returnTypeJava = "boolean",
    arguments = listOf(
        TestVariable(
            name = "command",
            javaType = "int"
        ),
    ),
    visibility = Visibility.PUBLIC
)




