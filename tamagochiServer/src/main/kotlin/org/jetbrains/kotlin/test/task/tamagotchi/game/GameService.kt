package org.jetbrains.kotlin.test.task.tamagotchi.game

import org.jetbrains.kotlin.test.task.tamagotchi.models.Command
import org.jetbrains.kotlin.test.task.tamagotchi.models.Mode
import org.springframework.stereotype.Service
import java.util.LinkedList

@Service
class GameService {
    companion object {
        private const val MAX_CAPACITY = 16
    }

    private val commandStorage: LinkedList<Command> = LinkedList<Command>()

    val copyOfCommandStorage: LinkedList<Command>
        get() = commandStorage
    fun getCommand(mode: String): Command? =
        when (parseMode(mode)) {
            Mode.Queue -> commandStorage.popFront()
            Mode.Stack -> commandStorage.popBack()
        }

    private fun parseMode(mode: String): Mode = when (mode) {
        "\"Queue\"" -> Mode.Queue
        "\"Stack\"" -> Mode.Stack
        else -> throw Exception("parseMode function failed!(my exception!)")
    }
    fun addCommand(command: Int): Boolean = commandStorage.addBack(parseCommand(command))

    private fun parseCommand(comm: Int): Command = when (comm % 4) {
        0 -> Command.Eat
        1 -> Command.Sleep
        2 -> Command.Clean
        3 -> Command.Play
        else -> throw Exception("parseCommand function failed!(my exception!)")
    }

    private fun LinkedList<Command>.popFront(): Command? = when (this.isEmpty()) {
        true -> null
        false -> commandStorage.removeFirst()
    }

    private fun LinkedList<Command>.popBack(): Command? = when (this.isEmpty()) {
        true -> null
        false -> commandStorage.removeLast()
    }

    private fun LinkedList<Command>.addBack(command: Command): Boolean = when(this.size < MAX_CAPACITY) {
        true -> {
            commandStorage.add(command)
            true
        }
        false -> false
    }
}
