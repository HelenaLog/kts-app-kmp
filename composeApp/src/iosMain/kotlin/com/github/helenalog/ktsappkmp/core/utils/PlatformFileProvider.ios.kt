package com.github.helenalog.ktsappkmp.core.utils

import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.usePinned
import kotlinx.io.Buffer
import kotlinx.io.RawSource
import kotlinx.io.Source
import kotlinx.io.buffered
import platform.Foundation.NSInputStream

@OptIn(ExperimentalForeignApi::class)
actual fun PlatformFile.toSource(): Source {
    val inputStream = NSInputStream(uRL = nsUrl)
    inputStream.open()

    return object : RawSource {
        private val chunk = ByteArray(8192)

        override fun readAtMostTo(sink: Buffer, byteCount: Long): Long {
            val toRead = minOf(byteCount, chunk.size.toLong()).toInt()
            val bytesRead = chunk.usePinned { pinned ->
                inputStream.read(pinned.addressOf(0).reinterpret(), toRead.toULong()).toLong()
            }
            if (bytesRead <= 0L) return -1L
            sink.write(chunk, 0, bytesRead.toInt())
            return bytesRead
        }

        override fun close() {
            inputStream.close()
        }
    }.buffered()
}
