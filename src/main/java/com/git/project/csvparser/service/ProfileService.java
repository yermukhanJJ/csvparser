package com.git.project.csvparser.service;

import com.git.project.csvparser.entity.ProfileTab;
import com.git.project.csvparser.payload.CsvData;
import com.git.project.csvparser.payload.ProfileRequest;
import com.git.project.csvparser.repository.ProfileRepo;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final ProfileRepo profileRepo;
    private final Environment env;

    public ProfileTab getProfileByItn(String itn) {
        log.info("Getting profile with itn: " + itn);
        return profileRepo.findByItn(itn)
                .orElseThrow(() -> new EntityNotFoundException("Profile with itn: " + itn + " not found!"));
    }

    @Transactional
    public void addNewProfile(ProfileRequest request) {
        ProfileTab profileTab = new ProfileTab(request.getFirstname(), request.getLastname(), request.getItn(), request.getStatus());
        profileRepo.save(profileTab);
        log.info("Add new profile: " + request.toString());
    }

    @Transactional
    public void editProfile(ProfileRequest request) {
        if (profileRepo.existsByItn(request.getItn())) {
            ProfileTab profile = profileRepo.findByItn(request.getItn())
                    .orElseThrow(() -> new EntityNotFoundException("Profile with itn: " + request.getItn() + " not found!"));
            profile.setFirstName(request.getFirstname());
            profile.setLastName(request.getLastname());
            profile.setItn(request.getItn());
            profile.setStatus(request.getStatus());
            profileRepo.save(profile);
            log.info("Profile: " + request.toString() + " update!");
        } else throw new EntityNotFoundException("Profile with itn: " + request.getItn() + "not found!");
    }

    @Transactional
    public void delProfile(String itn) {
        if (profileRepo.existsByItn(itn)) {
            log.info("Profile with itn: " + itn + " deleted!");
            profileRepo.deleteByItn(itn);
        } else throw new EntityNotFoundException("Profile with itn: " + itn + " not found!");
    }

    @Transactional
    public void csvParse() {

        try {
            String fileName = env.getProperty("cron.filePath");
            System.out.println(fileName);
            List<CsvData> data = new CsvToBeanBuilder<CsvData>(new FileReader(fileName))
                    .withType(CsvData.class)
                    .build()
                    .parse();

            for (CsvData csvData : data) {
                String itn = csvData.getItn();
                String status = csvData.getStatus();
                String firstname = csvData.getFirstName();
                String lastname = csvData.getLastName();

                if (profileRepo.existsByItn(itn)) {
                    ProfileTab oldProfile = profileRepo.findByItn(itn)
                       .orElseThrow(() -> new EntityNotFoundException("Itn: " + itn + " not found!"));
                    oldProfile.setStatus(status);
                    oldProfile.setFirstName(firstname);
                    oldProfile.setLastName(lastname);
                    log.info("update profile");
                    profileRepo.save(oldProfile);
                } else {
                    ProfileTab profile = new ProfileTab(firstname, lastname, itn, status);
                    log.info("save profile status");
                    profileRepo.save(profile);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
