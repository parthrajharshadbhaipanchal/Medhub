package org.asdc.medhub.Controller;

import org.asdc.medhub.Service.Interface.IChatService;
import org.asdc.medhub.Utility.Model.DatabaseModels.Chat;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.ChatDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class WebSocketChatControllerTest {
    @Mock
    public IChatService chatService;
    @Mock
    public SimpMessagingTemplate messagingTemplate;
    @InjectMocks
    public WebSocketChatController webSocketChatController;
    public Authentication authenticationImp;

    private ChatDetail makeChatDetail(long id) {
        ChatDetail chatDetail = new ChatDetail();
        chatDetail.setId(id);
        chatDetail.setSenderId("john@example.com");
        chatDetail.setReceiverId("doctor@example.com");
        chatDetail.setContent("This is test chat");
        chatDetail.setCreatedAt(Timestamp.from(Instant.now()));
        return chatDetail;
    }

    private Chat makeChat(long id) {
        Chat chat = new Chat();
        chat.setId(id);
        chat.setSenderId("john@example.com");
        chat.setReceiverId("doctor@example.com");
        chat.setContent("This is test chat");
        chat.setCreatedAt(Timestamp.from(Instant.now()));
        return chat;
    }

    @Test
    public void testSendPrivateMessage() {
        // Arrange
        ChatDetail chatDetail = makeChatDetail(1);
        Chat chat = makeChat(1);


        ResponseModel<Chat> expectedResponse = new ResponseModel<>();
        expectedResponse.setResponseData(chat);
        expectedResponse.setSuccess(true);

        when(chatService.postMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(chat);
        // Act
        ChatDetail actualResponse = webSocketChatController.sendPrivateMessage(chatDetail);

        // Assert
        assertEquals(chatDetail, actualResponse);
    }
}
