package org.asdc.medhub.Controller;

import org.asdc.medhub.Service.ChatService;
import org.asdc.medhub.Service.Interface.IChatService;
import org.asdc.medhub.Utility.Enums.UserRole;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.ChatDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.UserDetail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChatControllerTest
{
    @Mock
    public IChatService chatService;

    @InjectMocks
    public ChatController chatController;
    public Authentication authenticationImp;
    @Before
    public void setUp() {
        this.chatService = mock(ChatService.class);
        this.chatController = new ChatController(chatService);
        this.authenticationImp = new AuthenticationImp();
    }
    private ChatDetail makeChatDetail(long id) {
        ChatDetail chatDetail = new ChatDetail();
        chatDetail.setId(id);
        chatDetail.setSenderId("john@example.com");
        chatDetail.setReceiverId("doctor@example.com");
        chatDetail.setContent("This is test chat");
        chatDetail.setCreatedAt(Timestamp.from(Instant.now()));
        return chatDetail;
    }
    private UserDetail makeUserDetail() {
        UserDetail userDetail = new UserDetail();
        userDetail.setUserRole(UserRole.DOCTOR);
        userDetail.setLastName("john@example.com");
        userDetail.setFirstName("doctor@example.com");
        return userDetail;
    }
    @Test
    public void testGetConversation() {
        // Arrange
        ChatDetail chatDetail1 = makeChatDetail(1);
        ChatDetail chatDetail2 = makeChatDetail(2);


        List<ChatDetail> chatDetailList = new ArrayList<>();
        chatDetailList.add(chatDetail1);
        chatDetailList.add(chatDetail2);

        ResponseModel<List<ChatDetail>> expectedResponse = new ResponseModel<>();
        expectedResponse.setResponseData(chatDetailList);
        expectedResponse.setSuccess(true);

        when(chatService.getConversationBetweenTwoUsers(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<List<ChatDetail>>> responseEntity = chatController.getConversation(chatDetail1.getReceiverId(), authenticationImp);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse,responseEntity.getBody());
    }
    @Test
    public void testGetChatPartners() {
        // Arrange
        UserDetail userDetail = makeUserDetail();
        List<UserDetail> userDetailList = new ArrayList<>();
        userDetailList.add(userDetail);

        ResponseModel<List<UserDetail>> expectedResponse = new ResponseModel<>();
        expectedResponse.setResponseData(userDetailList);
        expectedResponse.setSuccess(true);

        when(chatService.findAllChatPartnersByUserId(Mockito.anyString()))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<List<UserDetail>>> responseEntity = chatController.getChatPartners(authenticationImp);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse,responseEntity.getBody());
    }
    @Test
    public void testGetUsername() {
        // Arrange
        String userName="";

        ResponseModel<String> expectedResponse = new ResponseModel<>();
        expectedResponse.setResponseData(userName);
        expectedResponse.setSuccess(true);

        when(chatService.getUsername(Mockito.any(User.class)))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<String>> responseEntity = chatController.getUserName(authenticationImp);

        //Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse,responseEntity.getBody());
    }
}
