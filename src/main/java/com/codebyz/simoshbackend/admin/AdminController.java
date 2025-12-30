package com.codebyz.simoshbackend.admin;

import com.codebyz.simoshbackend.admin.dto.AdminCreateRequest;
import com.codebyz.simoshbackend.admin.dto.AdminResponse;
import com.codebyz.simoshbackend.admin.dto.AdminUpdateRequest;
import com.codebyz.simoshbackend.auth.dto.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/super-admin/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<AdminResponse> create(@Valid @RequestBody AdminCreateRequest request) {
        return ResponseEntity.ok(adminService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<AdminResponse>> getAll() {
        return ResponseEntity.ok(adminService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(adminService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminResponse> update(@PathVariable UUID id,
                                                @Valid @RequestBody AdminUpdateRequest request) {
        return ResponseEntity.ok(adminService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable UUID id) {
        adminService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Admin muvaffaqiyatli o'chirildi"));
    }
}
