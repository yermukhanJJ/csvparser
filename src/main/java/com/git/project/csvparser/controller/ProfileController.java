package com.git.project.csvparser.controller;

import com.git.project.csvparser.entity.ProfileTab;
import com.git.project.csvparser.payload.ProfileRequest;
import com.git.project.csvparser.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{itn}")
    public ResponseEntity<ProfileTab> getProfile(@PathVariable(value = "itn") String itn) {
        return ResponseEntity.ok(profileService.getProfileByItn(itn));

    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addProfile(@RequestBody ProfileRequest request) {
        profileService.addNewProfile(request);

    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void editProfile(@RequestBody ProfileRequest request) {
        profileService.editProfile(request);

    }

    @DeleteMapping("/{itn}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delProfile(@PathVariable(value = "itn") String itn) {
        profileService.delProfile(itn);

    }

    @PostMapping("/index")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void indexCsv() {
        profileService.csvParse();

    }
}
