package com.codebyz.simoshbackend.about;

import com.codebyz.simoshbackend.about.dto.AboutRequest;
import com.codebyz.simoshbackend.about.dto.AboutResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/about")
public class AboutController {

    private final AboutService aboutService;

    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @GetMapping
    public ResponseEntity<AboutResponse> get() {
        return ResponseEntity.ok(aboutService.get());
    }

    @PutMapping
    public ResponseEntity<AboutResponse> update(@Valid @RequestBody AboutRequest request) {
        return ResponseEntity.ok(aboutService.update(request));
    }
}
