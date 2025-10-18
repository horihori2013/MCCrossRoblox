package com.github.horihori2013

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.*
class UUIDManager(private val dataFile: File) {

    private val uuidMap = mutableMapOf<Long, UUID>()
    private val config = YamlConfiguration()

    init {
        if (!dataFile.exists()) {
            dataFile.parentFile.mkdirs()
            dataFile.createNewFile()
        }
        load()
    }

    /** Roblox ID から UUID を取得（既存がなければ生成） */
    fun getUUIDForRobloxId(robloxId: Long): UUID {
        return uuidMap.getOrPut(robloxId) {
            generateStableUUID(robloxId)
        }
    }

    /** Roblox ID から UUID を安定生成 */
    private fun generateStableUUID(robloxId: Long): UUID {
        val namespace = UUID.nameUUIDFromBytes("mccrossroblox".toByteArray())
        return UUID.nameUUIDFromBytes("$namespace:$robloxId".toByteArray())
    }

    /** 設定ファイルからロード */
    private fun load() {
        config.load(dataFile)
        for (key in config.getKeys(false)) {
            val robloxId = key.toLongOrNull() ?: continue
            val uuidStr = config.getString(key) ?: continue
            uuidMap[robloxId] = UUID.fromString(uuidStr)
        }
    }

    /** 設定ファイルに保存 */
    fun save() {
        for ((robloxId, uuid) in uuidMap) {
            config.set(robloxId.toString(), uuid.toString())
        }
        config.save(dataFile)
    }
}