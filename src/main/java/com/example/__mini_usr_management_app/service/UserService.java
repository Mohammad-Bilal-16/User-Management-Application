package com.example.__mini_usr_management_app.service;

import com.example.__mini_usr_management_app.binding.LoginForm;
import com.example.__mini_usr_management_app.binding.UnlockAccountForm;
import com.example.__mini_usr_management_app.binding.UserForm;
import com.example.__mini_usr_management_app.entity.User;

import java.util.Map;

public interface UserService {

    public String checkEmail(String email);
    public Map<Integer , String> getCountries();
    public Map<Integer , String> getStates(Integer countryId);
    public Map<Integer , String> getCities(Integer stateId);
    public String registerUser(UserForm user);
    public String unlockAccount(UnlockAccountForm accountForm);
    public String login(LoginForm loginForm);
    public String forgotPassword(String email);

}
