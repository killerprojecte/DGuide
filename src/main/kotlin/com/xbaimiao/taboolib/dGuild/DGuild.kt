package com.xbaimiao.taboolib.dGuild

import fr.xephi.authme.api.v3.AuthMeApi
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.Plugin
import taboolib.common.platform.command
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Config
import taboolib.module.configuration.SecuredFile
import taboolib.platform.BukkitPlugin

object DGuild : Plugin() {

    val plugin by lazy {
        BukkitPlugin.getInstance()
    }

    @Config(value = "config.yml", migrate = true)
    lateinit var conf: SecuredFile
        private set

    @Config(value = "gui.yml", migrate = true)
    lateinit var confGui: SecuredFile
        private set

    val settings: ConfigurationSection get() = conf.getConfigurationSection("setting")

    val text: ConfigurationSection get() = conf.getConfigurationSection("text")

    override fun onEnable() {
        command(
            name = "dguide",
        ) {
            literal("start", optional = true) {
                execute<Player> { sender, _, _ ->
                    val authMeApi = AuthMeApi.getInstance()
                    if (authMeApi.isRegistered(sender.name)) {
                        Guild.isReg(sender)
                    } else {
                        Guild.isLogin(sender)
                    }
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

}