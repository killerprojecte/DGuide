package com.xbaimiao.taboolib.dGuild

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.module.configuration.Config
import taboolib.module.configuration.SecuredFile

object Data {

    @Config(value = "data.yml", migrate = true)
    lateinit var confData: SecuredFile
        private set

    fun Player.accept() {
        confData.set(this.uniqueId.toString(), true)
        Bukkit.getScheduler().runTaskAsynchronously(DGuild.plugin, Runnable {
            synchronized(Data) {
                confData.saveToFile()
            }
        })
    }

    fun Player.isAccept(): Boolean {
        return confData.getBoolean(this.uniqueId.toString())
    }

}