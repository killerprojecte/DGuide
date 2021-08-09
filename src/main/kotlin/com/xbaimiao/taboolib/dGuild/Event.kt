package com.xbaimiao.taboolib.dGuild

import com.xbaimiao.taboolib.dGuild.Data.isAccept
import fr.xephi.authme.api.v3.AuthMeApi
import fr.xephi.authme.events.LoginEvent
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.SubscribeEvent

object Event {

    @SubscribeEvent
    fun join(event: PlayerJoinEvent) {
        sync(10) {
            if (DGuild.settings.getBoolean("joinStart")) {
                val player = event.player
                player.teleport(DGuild.conf.getLocation("setting.location")!!)
                player.noDamageTicks = 3600
                val authMeApi = AuthMeApi.getInstance()
                if (!authMeApi.isRegistered(player.name)) {
                    Guild.isReg(player)
                } else {
                    Guild.isLogin(player)
                }
            }
        }
    }

    @SubscribeEvent
    fun login(event: LoginEvent) {
        if (DGuild.settings.getBoolean("joinStart")) {
            val player = event.player
            val cmd = Commands(1, DGuild.settings.getStringList("commands").colored())
            cmd.execute(player)
            Guild.bar[player]?.removeAll()
            player.sendTitle(null, null, 0, 0, 0)
            if (player.isAccept()) {
                return
            }
            Treaty.open(player)
        }
    }

    @SubscribeEvent
    fun quit(event: PlayerQuitEvent) {
        val player = event.player
        Guild.bar[player]?.removeAll()
    }

    @SubscribeEvent
    fun inv(event: InventoryClickEvent) {
        if (event.inventory.holder is Holder) {
            event.isCancelled = true
            val player = event.whoClicked as Player
            val slot = event.slot
            val commands = Treaty[slot] ?: return
            commands.execute(player)
        }
    }

    @SubscribeEvent
    fun close(event: InventoryCloseEvent) {
        sync(10) {
            if (event.inventory.holder is Holder) {
                val player = event.player as Player
                if (player.isAccept()) {
                    return@sync
                }
                Treaty.open(player)
            }
        }
    }

}