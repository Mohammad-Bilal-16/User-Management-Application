package com.example.__mini_usr_management_app.controller;

import com.example.__mini_usr_management_app.binding.LoginForm;
import com.example.__mini_usr_management_app.binding.UnlockAccountForm;
import com.example.__mini_usr_management_app.binding.UserForm;
import com.example.__mini_usr_management_app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserRestController {

    private UserService userService;
    /**
     *  This below methods is for Login Form
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm){
        String status = userService.login(loginForm);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
    /**
     *  This below methods is for Registration Form
     */
    @GetMapping("/countries")
    public Map<Integer , String> loadCountries(){
        return userService.getCountries();
    }
    @GetMapping("/states/{countryId")
    public Map<Integer, String> loadStates(@PathVariable Integer countryId){
        return userService.getStates(countryId);
    }

    @GetMapping("/cities/{stateId")
    public Map<Integer, String> loadCities(@PathVariable Integer stateId){
        return userService.getCities(stateId);
    }
    @GetMapping("/email/{email}")
    public String emailCheck(@PathVariable String email){
        return userService.checkEmail(email);
    }
    @PostMapping("/user")
    public ResponseEntity<String> userRegistration(@RequestBody UserForm userForm){
        String status = userService.registerUser(userForm);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }
    /**
     *  This below methods is for UnLock feature
     */
    @PostMapping("/unlock")
    public ResponseEntity<String> unLockAccount(@RequestBody UnlockAccountForm unlockAccountForm){
        String status = userService.unlockAccount(unlockAccountForm);
        return new ResponseEntity<>(status , HttpStatus.OK);
    }
    @GetMapping("/forgotpwd/{email}")
    public ResponseEntity<String> forgotPwd(@PathVariable String email){
        String status = userService.forgotPassword(email);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
