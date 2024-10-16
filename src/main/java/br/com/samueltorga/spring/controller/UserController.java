package br.com.samueltorga.spring.controller;

import br.com.samueltorga.spring.controller.dto.user.NewUserRequest;
import br.com.samueltorga.spring.controller.dto.user.UserResponse;
import br.com.samueltorga.spring.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<PagedModel<UserResponse>> getAllUsers(@PageableDefault(sort = "id") @NotNull final Pageable pageable) {
        Page<UserResponse> allProducts = userService.getAllUsers(pageable);
        return ResponseEntity.ok(new PagedModel<>(allProducts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Integer id) {
        Optional<UserResponse> product = userService.getUserById(id);
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@NotNull @Valid @RequestBody NewUserRequest newUserRequest) {
        UserResponse user = userService.createUser(newUserRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}