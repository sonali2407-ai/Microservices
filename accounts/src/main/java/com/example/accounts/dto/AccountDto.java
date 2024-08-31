package com.example.accounts.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AccountDto {
    @NotEmpty // validation annotation
    @Pattern(regexp =("$|[0-9]{10}"), message="Mobile no should be of 10 digit")
    private Long accountNumber;

    @NotEmpty(message="Account type cant be null")
    private String accountType;

    @NotEmpty(message="branch address cant be empty")
    private String branchAddress;
}
