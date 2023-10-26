@file:Suppress("UnusedParameter")

package org.jetbrains.kotlin.test.task.tamagotchi.game

import org.jetbrains.kotlin.test.task.tamagotchi.models.Command
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/command/")
class GameResource(val service: GameService) {
    @CrossOrigin
    @PostMapping("/get")
    fun getCommand(@RequestBody mode: String): Command? = service.getCommand(mode)

    @CrossOrigin
    @PostMapping("/add")
    fun addCommand(@RequestBody command: Int): Boolean = service.addCommand(command)

    @CrossOrigin
    @GetMapping("/all")
    fun getAllCommands(): ArrayDeque<Command> = ArrayDeque(service.copyOfCommandStorage)

}
