package com.xbaimiao.taboolib.dGuild

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.Plugin
import taboolib.common.platform.command
import taboolib.module.configuration.Config
import taboolib.module.configuration.SecuredFile
import taboolib.platform.BukkitPlugin

object DGuild : Plugin() {

    val plugin by lazy {
        BukkitPlugin.getInstance()
    }

    init {
        command(
            name = "DGuide",
        ) {
            literal("start", optional = true) {
                execute<Player> { sender, _, argument ->
                    sender.sendMessage(argument)
                }
            }
            execute<CommandSender> { sender, _, _ ->
                if (sender is Player) {
                    sender.sendMessage("/DGuide start -> 开始新手向导")
                } else {
                    sender.sendMessage("/DGuide start -> 开始新手向导(仅玩家可用)")
                }
            }
        }
    }

    @Config(value = "config.yml", migrate = true)
    lateinit var conf: SecuredFile
        private set

    @Config(value = "gui.yml", migrate = true)
    lateinit var confGui: SecuredFile
        private set

    val settings get() = conf.getConfigurationSection("setting")

    override fun onEnable() {

    }

}