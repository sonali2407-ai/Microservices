package com.example.accounts.controller;

import com.example.accounts.constants.AccountConstant;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.dto.ResponseDto;
import com.example.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated // this tells spring boot to perform validation for all the api of controller
@Tag(name="CRUD REST API for Account microservices ",
        description="Contains read delete update and insert operation"
) // @TAg is part of openAPi dependency to to documentation , use swagger to check
public class AccountsController {
 IAccountService iAccountService;

 @Operation(summary=" Create Account Rest API",
 description= "Rest API to create a new account and a new Customer")
 @ApiResponse(responseCode = "201",
         description ="HTTP  Status Created") // to change default 200 status, we can provide our status here
    @PostMapping("/create")// responseentity can be used to send header,body and status
    public ResponseEntity<ResponseDto > createAccount(@Valid @RequestBody CustomerDto customerDto) // @valid will tell spring boot to perform all validation that we mentioned inside this DTO
    {  iAccountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstant.STATUS_201,AccountConstant.MESSAGE_201));
    }
    @Operation(summary=" Fetch Account Rest API",
            description= "Rest API to fetch  account and  Customer details ")

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccount(@RequestParam @Pattern(regexp =("$|[0-9]{10}"), message="Mobile no should be of 10 digit") String mobileNo)
    { // @pattern we are sccepting regex exp
        CustomerDto customerDto= iAccountService.fetchAccount(mobileNo);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);

    }
    @Operation(summary=" Update Account Rest API",
            description= "Rest API to Update  account and  Customer details ")
    @ApiResponse(responseCode = "200", // to change default status code
    description = "HttpStatus ok")
    @ApiResponse(responseCode = "417",
            description = "HttpStatus InternalServerError")

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        boolean flag = iAccountService.updateAccount(customerDto);
        if (flag) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstant.STATUS_417,AccountConstant.MESSAGE_417_UPDATE));
        }

    }

    @Operation(summary=" Delete Account Rest API",
            description= "Rest API to Delete account and  Customer details ")
    @ApiResponse(responseCode = "200",
            description = "HttpStatus ok")
    @ApiResponse(responseCode = "417",
            description = "HttpStatus InternalServerError")
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam @Pattern(regexp =("$|[0-9]{10}"), message="Mobile no should be of 10 digit") String mobileNo)
    {
        Boolean flag= iAccountService.deleteAccount(mobileNo);
        if(flag)
        {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountConstant.STATUS_200,AccountConstant.MESSAGE_200));

        }
        else
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountConstant.STATUS_500,AccountConstant.MESSAGE_500));
    }

}
