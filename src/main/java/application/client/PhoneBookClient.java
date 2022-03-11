package application.client;

import application.container.dto.StatusDto;
import application.container.dto.UserOp;
import application.container.dto.UserReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@FeignClient(value = "phonebook-api", url = "${backend.phonebook-backend-api}")
public class PhoneBookClient {

    @GetMapping(value = "/user/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserOp> getAllUsers();

    @PostMapping(value = "/user/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserReq postUser(@RequestBody UserReq userRequestData);

    @PutMapping(value = "/user/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserOp editUser(@RequestBody UserReq userRequestData);

    @DeleteMapping(value = "/user/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserOp deleteUser(@RequestBody UserReq userRequestData);

    @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatusDto status();
}
