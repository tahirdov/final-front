package application.controller;


import application.client.PhoneBookClient;
import application.container.dto.OperationDto;
import application.container.dto.UserReq;
import application.container.dto.UserOp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final PhoneBookClient client;

    @GetMapping("/")
    public String listUsers(Model model) {
        List<UserOp> users = client.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("userEntity", new UserReq());
        return "user";
    }

    @PostMapping("/user/add")
    public String addUser(@ModelAttribute UserReq userRequestData, Model model) {
        var userOperation = client.postUser(userRequestData);
        model.addAttribute("operation", "Add");
        return "operation";
    }

    @PostMapping("/user/edit")
    public String editUser(@ModelAttribute UserReq userRequestData, Model model) {
        OperationDto userOperation = client.editUser(userRequestData);
        model.addAttribute("operation", "Edit");
        return "operation";
    }

    @PostMapping("/user/delete")
    public String deleteUser(@ModelAttribute UserReq userRequestData, Model model) {
        OperationDto userOperation = client.deleteUser(userRequestData);
        model.addAttribute("operation", "Delete");
        return "operation";
    }

    @GetMapping("/status")
    public String status(Model model) {
        System.out.println("Something");
        var status = client.status().getStatus().value();
        System.out.println(status);
        model.addAttribute("status", status);
        System.out.println("I am here");
        return "status";
    }
}
