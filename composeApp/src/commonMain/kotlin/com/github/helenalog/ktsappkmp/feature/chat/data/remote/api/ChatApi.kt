package com.github.helenalog.ktsappkmp.feature.chat.data.remote.api

import com.github.helenalog.ktsappkmp.core.data.remote.response.ApiResponse
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.AttachmentUploadDto
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.ConversationLiteDto
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.request.SendMessageRequestDto
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.response.MessagesResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully

class ChatApi(private val client: HttpClient) {

    suspend fun getConversationLite(
        conversationId: Long,
        userId: String,
    ): ApiResponse<ConversationLiteDto> =
        client.get("api/conversations/get_conversation_lite") {
            parameter("id", conversationId)
            parameter("user_id", userId)
        }.body()

    suspend fun getMessages(
        conversationId: Long,
        userId: String,
        channelId: String,
        limit: Int = DEFAULT_MESSAGES_LIMIT,
        fromId: String? = null,
    ): ApiResponse<MessagesResponseDto> =
        client.get("api/conversations/list_messages") {
            parameter("conversation_id", conversationId)
            parameter("user_id", userId)
            parameter("channel_id", channelId)
            parameter("limit", limit)
            fromId?.let { parameter("from_id", it) }
        }.body()

    suspend fun sendMessage(request: SendMessageRequestDto): ApiResponse<Unit> =
        client.post("api/conversations/send_message") {
            setBody(request)
        }.body()

    suspend fun uploadAttachment(
        fileName: String,
        bytes: ByteArray,
    ): ApiResponse<AttachmentUploadDto> {
        val response = client.post("api/attachments/upload") {
            setBody(
                MultiPartFormDataContent(formData {
                    appendInput(
                        key = "file",
                        headers = Headers.build {
                            append(
                                name = HttpHeaders.ContentDisposition,
                                value = "filename=\"$fileName\""
                            )
                        },
                        size = bytes.size.toLong(),
                    ) { buildPacket { writeFully(bytes) } }
                })
            )
        }
        return response.body()
    }

    private companion object {
        const val DEFAULT_MESSAGES_LIMIT = 20
    }
}