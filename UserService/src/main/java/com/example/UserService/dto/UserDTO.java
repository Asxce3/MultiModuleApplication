package com.example.UserService.DTO;

import com.example.UserService.DTO.transfer.New;
import com.example.UserService.DTO.transfer.Update;
import com.example.UserService.annotations.validation.password.ValidPassword;
import com.example.UserService.annotations.validation.telephone.ValidPhoneRegion;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.util.UUID;

@Data
@ValidPhoneRegion(groups = {New.class, Update.class})

public class UserDTO {

    @Null
    private UUID id;

    @NotNull(groups = New.class)
    @Null(groups = {Update.class})
    private String username;

    @NotNull(groups = New.class)
    @ValidPassword
    @Null(groups = {Update.class})
    private String password;

    @Email(groups = {New.class, Update.class})
    private String email;

    private String telephone;
    @Null
    private String country;


}
