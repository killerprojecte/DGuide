package com.xbaimiao.taboolib.dGuild

import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player

object Guild {

    val bar = HashMap<Player, BossBar>()

    fun isLogin(player: Player) {
        val bossbar = DGuild.text.getString("login.bossbar").colored()
        val title1 = DGuild.text.getString("login.title").colored().split("\n")[0]
        val title2 = DGuild.text.getString("login.title").colored().split("\n")[1]
        bar[player] = Bukkit.createBossBar(bossbar, BarColor.BLUE, BarStyle.SOLID).let {
            it.addPlayer(player)
            it
        }
        player.sendTitle(title1, title2, 0, 2000, 2000)
    }

    fun isReg(player: Player) {
        val bossbar = DGuild.text.getString("reg.bossbar").colored()
        val title1 = DGuild.text.getString("reg.title").colored().split("\n")[0]
        val title2 = DGuild.text.getString("reg.title").colored().split("\n")[1]
        bar[player] = Bukkit.createBossBar(bossbar, BarColor.BLUE, BarStyle.SOLID).let {
            it.addPlayer(player)
            it
        }
        player.sendTitle(title1, title2, 0, 2000, 2000)
    }

}