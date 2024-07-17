package ra.project_md05.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_md05.model.dto.response.UserResponse;
import ra.project_md05.model.dto.response.converter.UserConverter;
import ra.project_md05.model.entity.Users;
import ra.project_md05.service.IRoleService;
import ra.project_md05.service.IUserService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
		@Autowired
		IUserService userService;
		@Autowired
		private IRoleService roleService;

	@GetMapping("/users")
	public ResponseEntity<?> getUsers(@RequestParam(defaultValue = "0") int page,
									  @RequestParam(defaultValue = "2") int size,
									  @RequestParam(defaultValue = "userId") String sortField,
									  @RequestParam(defaultValue = "asc") String sortDirection) {

		Page<Users> users = userService.getAllUser(page, size, sortField, sortDirection);
		return new ResponseEntity<>(users.getContent(), HttpStatus.OK);
	}

	//lay ve danh sach cac quyen
	@GetMapping("/roles")
	public ResponseEntity<?> getAllRoles() {
		return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);

	}

	//search user theo tên
	@GetMapping("/users/search")
	public ResponseEntity<?> getUsersByName(@RequestParam("name") String name) {
		return new ResponseEntity<>(userService.findByUsernameContainingIgnoreCase(name), HttpStatus.OK);
	}

	//khoa mo tai khoan nguoi dung
	@PutMapping("/users/{userId}")
	public ResponseEntity<UserResponse> changeUserStatus(@PathVariable Long userId, @RequestParam Boolean status) {
		Users changeUserStatus = userService.updateUserStatus(userId, status);
		return new ResponseEntity<>(UserConverter.toUserResponse(changeUserStatus), HttpStatus.OK);
	}

}
