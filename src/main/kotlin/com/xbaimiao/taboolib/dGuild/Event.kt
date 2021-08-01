package com.xbaimiao.taboolib.dGuild

import com.xbaimiao.taboolib.dGuild.Data.isAccept
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.SubscribeEvent

object Event {

    @SubscribeEvent
    fun join(event: PlayerJoinEvent) {
        sync(30) {
            if (DGuild.settings.getBoolean("joinStart")) {
                val player = event.player
                if (player.isAccept()) {
                    return@sync
                }
                Treaty.open(player)
            }
        }
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

}