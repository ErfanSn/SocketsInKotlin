package udp

import kotlinx.coroutines.*
import udp.Utility.openAndStartSpeakers
import udp.Utility.wavAudioFormat
import java.net.DatagramPacket
import java.net.DatagramSocket

class SimpleAudioStreamClient {

    private val speakers = openAndStartSpeakers(wavAudioFormat)

    private val socket = DatagramSocket(PORT)
    private val buffer = ByteArray(BUFFER_SIZE)

    suspend fun listen() {
        while (true) {
            val datagramPacket = DatagramPacket(buffer, buffer.size)
            withContext(Dispatchers.IO) {
                socket.receive(datagramPacket)
                speakers.write(buffer, 0, buffer.size)
            }
        }
    }
}
