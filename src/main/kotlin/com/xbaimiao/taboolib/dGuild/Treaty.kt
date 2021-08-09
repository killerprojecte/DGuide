package com.xbaimiao.taboolib.dGuild

import com.xbaimiao.taboolib.dGuild.Data.accept
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import taboolib.module.configuration.SecuredFile

object Treaty {

    val items = ArrayList<Item>()

    fun open(player: Player) {
        player.openInventory(treatyGui)
    }

    operator fun get(slot: Int): Commands? {
        return items.let {
            var i: Commands? = null
            for (item in it) {
                if (item.slot == slot) {
                    i = item.commands
                }
            }
            i
        }
    }

    val treatyGui by lazy {
        val gui =
            Bukkit.createInventory(
                Holder,
                9 * DGuild.confGui.getInt("line"),
                DGuild.confGui.getString("title").colored()
            )
        val items = DGuild.confGui.getConfigurationSection("items")
        for (key in items.getKeys(false)) {
            key.let {
                val material = Material.valueOf(items.getString("$it.material").uppercase())
                val slot = items.getInt("$it.slot")
                val name = items.getString("$it.name").colored()
                val lore = items.getStringList("$it.lore").colored()
                val commands = items.getStringList("$it.commands").colored()
                val c = Commands(slot, commands)
                val i = Item(material, slot, name, lore, c)
                Treaty.items.add(i);
            }
        }
        Treaty.items.forEach { item ->
            gui.setItem(
                item.slot,
                ItemStack(item.material).let {
                    val meta = it.itemMeta!!
                    meta.setDisplayName(item.name)
                    meta.lore = item.lore
                    it.itemMeta = meta
                    it
                }
            )
        }
        gui
    }

}

data class Item(val material: Material, val slot: Int, val name: String, val lore: List<String>, val commands: Commands)

class Commands(val slot: Int, private val list: List<String>) {

    fun execute(sender: Player) {
        for (s in list) {
            if (s.lowercase().startsWith("kick: ")) {
                sender.kickPlayer(s.split(": ")[1].colored())
            }
            if (s.lowercase().startsWith("tell: ")) {
                sender.sendMessage(s.split(": ")[1].colored())
            }
            if (s.lowercase().startsWith("player: ")) {
                Bukkit.dispatchCommand(sender, s.split(": ")[1].colored())
            }
            if (s.lowercase().startsWith("console: ")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.split(": ")[1].colored())
            }
            if (s.lowercase().startsWith("close")) {
                sender.closeInventory()
            }
            if (s.lowercase().startsWith("accept")) {
                sender.accept()
            }
        }
    }

}

object Holder : InventoryHolder {
    override fun getInventory(): Inventory {
        TODO("Not yet implemented")
    }
}

fun String.colored(): String {
    return ChatColor.translateAlternateColorCodes('&', this)
}

fun List<String>?.colored(): List<String> {
    val l = ArrayList<String>()
    this ?: return emptyList()
    this.forEach { ll ->
        l.add(ll.colored())
    }
    return l
}

fun sync(int: Long, func: Runnable) {
    Bukkit.getScheduler().runTaskLater(DGuild.plugin, func, int)
}

fun SecuredFile.getLocation(path: String): Location? {
    val world = Bukkit.getWorld(this.getString("$path.world")) ?: return null
    val x = this.getDouble("$path.x")
    val y = this.getDouble("$path.y")
    val z = this.getDouble("$path.z")
    val yaw = this.getDouble("$path.yaw").toFloat()
    val pitch = this.getDouble("$path.pitch").toFloat()
    return Location(world, x, y, z, yaw, pitch)
}