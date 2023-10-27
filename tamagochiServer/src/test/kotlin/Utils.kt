import org.jetbrains.kotlin.test.task.tamagotchi.game.GameService
import kotlin.random.Random

internal const val TEST_REPEAT_AMOUNT = 10
internal const val TEST_DATA_SIZE = 10

fun initRandomData(service: GameService) = List(TEST_DATA_SIZE) {
    _ -> service.addCommand(Random.nextInt())
}