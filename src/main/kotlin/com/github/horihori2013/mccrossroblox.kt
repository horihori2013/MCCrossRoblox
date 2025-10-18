package com.github.horihori2013

import io.papermc.paper.command.brigadier.Commands
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import com.github.horihori2013.WebSocketServer

lateinit var uuidManager: UUIDManager
class Mccrossroblox : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        server.scheduler.runTask(this) {
            initializeUUIDs()
            initializeChat()
            initializeEconomy()
            registerCommands()

        }
        logger.info("MCCrossRoblox enabled!")
        wsServer = WebSocketServer(8081)
        wsServer.start()
        val uuidFile = File(dataFolder, "roblox-uuid.yml")
        uuidManager = UUIDManager(uuidFile)

        logger.info("UUIDManager loaded with ${uuidManager}")
    }

    private fun initializeUUIDs() {
        UUIDManager.load()
    }

    private fun initializeChat() {
        ChatManager.load()
    }

    private fun initializeEconomy() {
        EconomyManager.load()
    }

    private fun registerCommands() {
        Commands.register(this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
        uuidManager.save()
    }
}
