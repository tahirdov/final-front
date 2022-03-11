package application.controller;

import application.client.PhoneBookClient;
import application.container.dto.OperationDto;
import application.container.dto.StatusDto;
import application.container.dto.UserOp;
import application.container.dto.UserReq;
import application.container.enums.OperationStatuses;
import application.container.enums.OperationTypes;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = UserController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE)
)

public class UserControllerTest {
    @Autowired
    private UserController controller;
    @MockBean
    private PhoneBookClient client;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final UUID userId = UUID.randomUUID();
    private final UserReq req = getUserRequestData();
    private final UserOp user = getUserDto();

    private UserOp getUserDto() {
        return UserOp.builder()
                .user_id(UUID.randomUUID())
                .name("Name")
                .phone("0123456").build();
    }

    private UserReq getUserRequestData() {
        return UserReq.builder()
                .name("Name")
                .phone("0123456").build();
    }

    @SneakyThrows
    @Test
    public void addUserSuccess() {
        UserReq userRequestData = UserReq.builder()
                .name("Name")
                .phone("1234567")
                .build();
        when(client.postUser(any())).thenReturn(userRequestData);
        final String contentAsString = mockMvc.perform(post("/user/add")
                .content(objectMapper.writeValueAsString(userRequestData))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(contentAsString, is(not(equalTo(null))));
    }

    @SneakyThrows
    @Test
    public void editUserSuccess() {
        OperationDto userOperation = OperationDto.builder()
                .operation_status(OperationStatuses.SUCCESS)
                .operation_type(OperationTypes.OPERATION_EDIT)
                .build();
        UserReq userRequestData = UserReq.builder()
                .name("Tahir")
                .phone("12547852")
                .build();
        when(client.editUser(any())).thenReturn(userOperation);
        final String contentAsString = mockMvc.perform(
                post("/user/edit")
                        .content(objectMapper.writeValueAsString(userRequestData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(contentAsString, is(not(equalTo(null))));
    }

    @SneakyThrows
    @Test
    public void getUserListSuccess() {
        when(client.getAllUsers()).thenReturn(Collections.singletonList(user));
        final String contentAsString = mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(contentAsString, is(not(equalTo(null))));
    }

    @Test
    public void deleteUser() throws Exception {
        OperationDto userOperation = OperationDto.builder()
                .operation_status(OperationStatuses.SUCCESS)
                .operation_type(OperationTypes.OPERATION_DELETE)
                .build();
        when(client.deleteUser(any())).thenReturn(userOperation);
        final String contentAsString = mockMvc.perform(
                post("/user/delete")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(contentAsString, is(not(equalTo(null))));
    }


    @Test
    public void getStatusInfoTest() throws Exception {
        when(client.status()).thenReturn(StatusDto.builder().status(HttpStatus.OK).build());
        final String contentAsString = mockMvc.perform(
                get("/status"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(contentAsString, is(not(equalTo(null))));
    }
}
