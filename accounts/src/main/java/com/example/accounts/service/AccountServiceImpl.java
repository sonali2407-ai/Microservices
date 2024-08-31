package com.example.accounts.service;

import com.example.accounts.constants.AccountConstant;
import com.example.accounts.dto.AccountDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Accounts;
import com.example.accounts.entity.Customer;
import com.example.accounts.exception.CustomerAlreadyExistsException;
import com.example.accounts.exception.ResourceNotFoundException;
import com.example.accounts.mapper.AccountMapper;
import com.example.accounts.mapper.CustomerMapper;

import com.example.accounts.repository.AccountsRepository;
import com.example.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

// All the business logics are written in service layer.
@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService{

    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * @param customerDto
     * we arte taking customer details and saving it in cutomerdatabase.
     * along with that we are using that customer details to create an account for that customer.
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer= CustomerMapper.mapToCustomer(customerDto,new Customer());
        Optional<Customer> optionalCustomer= customerRepository.findByMobileNo(customerDto.getMobileNo());
        if(optionalCustomer.isPresent())//is present method will check whether optional obeject has record of customer or not
        {
            throw new CustomerAlreadyExistsException("Mobile number is already registered"+customerDto.getMobileNo());
        }
       // customer.setCreatedAt(LocalDateTime.now());
        //customer.setCreatedBy("Me");
        Customer savedCustomer= customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));// calling method to create new account
    }
    public Accounts createNewAccount(Customer customer)
    {
        Accounts newAccount= new Accounts();
        newAccount.setCustomerId(customer.getCustomerId()); // taking customer id from customer deatils;
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstant.SAVINGS);
        newAccount.setBranchAddress(AccountConstant.ADDRESS);
        //    newAccount.setCreatedAt(LocalDateTime.now());
        //   newAccount.setCreatedBy("Bank");
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNo) {
        Customer customer= customerRepository.findByMobileNo(mobileNo).orElseThrow(()->
                new ResourceNotFoundException("Customer","mobileNo",mobileNo));
        Accounts account= accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(()->
                new ResourceNotFoundException("Account","customerID",customer.getCustomerId().toString()));
        // here we want to send both account details and customer details but not any sensitive info which is present in base entity so we will map accountDto into customerDto and will return customerDto;
        CustomerDto customerDto= CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        AccountDto accountDto= AccountMapper.mapToAccountsDto(account,new AccountDto());
        customerDto.setAccountDto(accountDto);
        return customerDto;
    }
// save method if finds primary key inside the data then it update the records, if primary key not present then it perform insert opeartion.
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
       boolean isUpdated=false;
        AccountDto accountDto= customerDto.getAccountDto();

        if(accountDto!=null)
        {
          Accounts accounts=  accountsRepository.findById(accountDto.getAccountNumber().toString()).orElseThrow(
                  ()-> new ResourceNotFoundException("Account","AcccountNo",accountDto.getAccountNumber().toString())
          );
           AccountMapper.mapToAccounts(accountDto,accounts);
           accounts=accountsRepository.save(accounts);

           Long customerId= accounts.getCustomerId();
           Customer customer= customerRepository.findById(customerId.intValue()).orElseThrow(
                   ()-> new ResourceNotFoundException("Customer","CustomerID",customerId.toString())
           );
           CustomerMapper.mapToCustomer(customerDto,customer);
           customer= customerRepository.save(customer);

           isUpdated=true;
        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNo) {
        boolean isDeleted=false;
       Customer customer= customerRepository.findByMobileNo(mobileNo).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","Mobileno",mobileNo)
        );
       if(customer!=null)
       {
         accountsRepository.deleteByCustomerId(customer.getCustomerId());
         customerRepository.deleteById(customer.getCustomerId().intValue());
           isDeleted=true;
       }
       return isDeleted;
    }





}
