package com.bookflow.test;

import com.bookflow.auth.dto.request.RegisterRequest;
import com.bookflow.auth.service.abstraction.AuthService;
import com.bookflow.auth.service.concrete.AuthServiceHandler;
import com.bookflow.user.entity.UserEntity;
import com.bookflow.user.enums.StatusEnum;
import com.bookflow.user.enums.UserEnum;
import com.bookflow.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Hidden
@RequestMapping("/test")
public class TestDataController {

    private final AuthServiceHandler authService;
    private final UserRepository userRepository;

    @PostMapping("/create-users")
    public String createTestUsers() {

        String[][] users = {
                {"Zamiq", "Rahmanov"},
                {"Tural", "Mammadov"},
                {"Elvin", "Hasanov"},
                {"Murad", "Aliyev"},
                {"Orxan", "Huseynov"},
                {"Kamran", "Karimov"},
                {"Nicat", "Ismayilov"},
                {"Samir", "Mehdiyev"},
                {"Farid", "Abbasov"},
                {"Anar", "Quliyev"},
                {"Emil", "Suleymanov"},
                {"Rauf", "Aslanov"},
                {"Emin", "Jafarov"},
                {"Ilkin", "Rustamov"},
                {"Vusal", "Nabiyev"},
                {"Aydin", "Hasanli"},
                {"Cavid", "Mammadli"},
                {"Nurlan", "Hajiyev"},
                {"Zaur", "Aliyev"},
                {"Shahin", "Mustafayev"},
                {"Adil", "Babayev"},
                {"Ruslan", "Safarov"},
                {"Tofiq", "Isgandarov"},
                {"Javid", "Taghiyev"},
                {"Yusif", "Rahmanov"},
                {"Arif", "Karimov"},
                {"Omer", "Mammadov"},
                {"Faiq", "Hasanov"},
                {"Sanan", "Yaqublu"},
                {"Ramil", "Huseynov"}
        };

        int createdCount = 0;

        for (String[] user : users) {

            String firstName = user[0];
            String lastName = user[1];

            String email = firstName.toLowerCase()
                    + "."
                    + lastName.toLowerCase()
                    + "@test.com";

            if (userRepository.findByEmail(email).isPresent()) {
                continue;
            }

            RegisterRequest request = new RegisterRequest(
                    firstName,
                    lastName,
                    email,
                    "Password123!",
                    "CUSTOMER"
            );


            authService.registerUser(request);


            UserEntity savedUser = userRepository
                    .findByEmail(email)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "User not found after registration: " + email
                            )
                    );

            savedUser.setStatus(StatusEnum.ACTIVE);
            savedUser.setRole(UserEnum.CUSTOMER);
            userRepository.save(savedUser);
            createdCount++;
        }

        return createdCount + " test users created successfully";
    }


}