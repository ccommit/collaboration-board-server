package com.commit.collaboration_board_server.controller;

import com.commit.collaboration_board_server.dto.request.RoleUpsertRequest;
import com.commit.collaboration_board_server.dto.request.UserCreateRequest;
import com.commit.collaboration_board_server.model.Role;
import com.commit.collaboration_board_server.model.User;
import com.commit.collaboration_board_server.model.Vacation;
import com.commit.collaboration_board_server.service.RoleService;
import com.commit.collaboration_board_server.service.UserService;
import com.commit.collaboration_board_server.service.VacationService;
import com.commit.collaboration_board_server.util.ResponseStatusUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final VacationService vacationService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest, HttpSession session) {
        boolean isAuthenticated = userService.authenticate(loginRequest);

        if (!isAuthenticated) {
            return ResponseEntity.status(ResponseStatusUtil.getStatus("UNAUTHORIZED")).body("Invalid userId or password.");
        }

        userService.saveUserSession(session, loginRequest);
        return ResponseEntity.status(ResponseStatusUtil.getStatus("SUCCESS")).body("Login successful.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        userService.removeUserSession(request);
        return ResponseEntity.status(ResponseStatusUtil.getStatus("SUCCESS")).body("Logout successful.");
    }

    @GetMapping
    public List<User> getAllUsers(@RequestParam(required = false) Long userNo) {
        return userService.getUsers(userNo);
    }


    @PostMapping
    public void createUser(@RequestBody UserCreateRequest user) {
        userService.createUser(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setUserNo(id);
        userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/roles")
    public String createRole(@RequestBody RoleUpsertRequest role) {
        roleService.insertRole(role);
        return "Role added successfully!";
    }

    @PostMapping("/roles/{id}")
    public String updateRole(@PathVariable Long id, @RequestBody RoleUpsertRequest role) {
        roleService.updateRole(id, role);
        return "Role added successfully!";
    }

    @PostMapping("/vacations")
    public String applyVacation(@RequestBody Vacation vacation) {
        vacationService.applyVacation(vacation);
        return "휴가 신청이 완료되었습니다.";
    }
    @PostMapping("/vacations/{vacationId}/approve")
    public String approveVacation(@PathVariable int vacationId) {
        vacationService.approveVacation(vacationId);
        return "휴가가 승인되었습니다.";
    }
    @PostMapping("/vacations/{vacationId}/reject")
    public String rejectVacation(@PathVariable int vacationId) {
        vacationService.rejectVacation(vacationId);
        return "휴가가 반려되었습니다.";
    }
    @GetMapping("/vacations")
    public List<Vacation> getAllVacations() {
        return vacationService.getAllVacations();
    }
}