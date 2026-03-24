import com.github.helenalog.ktsappkmp.core.utils.DateFormatter
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.AttachmentDto
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.ConversationLiteDto
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatAttachment
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatAttachmentType
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatMessage
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ConversationDetail
import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.MessageDto
import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.MessageKindDto
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind

fun ConversationLiteDto.toDomain() = ConversationDetail(
    userId = user.id,
    userName = listOfNotNull(user.firstName, user.lastName).joinToString(" "),
    photoUrl = user.photo?.url,
    channelId = channel.id,
    botName = channel.name,
    channelKind = ChannelKind.UNKNOWN,
    stoppedByManager = state.stoppedByManager,
)

fun MessageDto.toDomain() = ChatMessage(
    id = id,
    kind = kind.toDomain(),
    text = text,
    time = dateCreated?.let { DateFormatter.formatToShortTime(it) },
    date = dateCreated?.let { DateFormatter.formatToIsoDate(it) },
    attachments = attachments.map { it.toDomain() },
)

fun MessageKindDto.toDomain(): MessageKind = when (this) {
    MessageKindDto.BOT -> MessageKind.BOT
    MessageKindDto.SERVICE -> MessageKind.SERVICE
    MessageKindDto.MANAGER -> MessageKind.MANAGER
    MessageKindDto.USER -> MessageKind.USER
}

fun AttachmentDto.toDomain() = ChatAttachment(
    id = id.orEmpty(),
    type = if (type?.lowercase() == "image") ChatAttachmentType.IMAGE else ChatAttachmentType.FILE,
    url = url,
    name = filename ?: name,
    mimeType = type,
    size = size,
)