package com.github.horihori2013

import org.java_websocket.server.WebSocketServer
import org.java_websocket.handshake.ClientHandshake
import java.net.InetSocketAddress
import java.net.http.WebSocket

class WebSocketServer(port: Int) : WebSocketServer(InetSocketAddress(port)) {

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        println("Roblox client connected: ${conn.remoteSocketAddress}")
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        println("Roblox client disconnected: ${conn.remoteSocketAddress}")
    }

    override fun onMessage(conn: WebSocket, message: String) {
        println("Received from Roblox: $message")
        // TODO: PacketHandlerに処理を委譲
    }

    override fun onError(conn: WebSocket?, ex: Exception) {
        ex.printStackTrace()
    }

    override fun onStart() {
        println("Roblox WebSocket server started on port ${address.port}")
    }
}
