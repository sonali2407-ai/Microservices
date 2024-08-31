package com.example.accounts.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message="name cant be empty")
    @Size(min=5,max=30)
    String name;

    @NotEmpty
    @Email(message="this is not the correct format of email.")
    String email;

    @NotEmpty
    @Pattern(regexp =("$|[0-9]{10}"), message="Mobile no should be of 10 digit")
    String mobileNo;

    AccountDto accountDto;
}
