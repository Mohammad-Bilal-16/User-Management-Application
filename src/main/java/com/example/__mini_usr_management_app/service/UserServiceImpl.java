package com.example.__mini_usr_management_app.service;

import com.example.__mini_usr_management_app.binding.LoginForm;
import com.example.__mini_usr_management_app.binding.UnlockAccountForm;
import com.example.__mini_usr_management_app.binding.UserForm;
import com.example.__mini_usr_management_app.entity.City;
import com.example.__mini_usr_management_app.entity.Country;
import com.example.__mini_usr_management_app.entity.State;
import com.example.__mini_usr_management_app.entity.User;
import com.example.__mini_usr_management_app.repository.CityRepository;
import com.example.__mini_usr_management_app.repository.CountryRepository;
import com.example.__mini_usr_management_app.repository.StateRepository;
import com.example.__mini_usr_management_app.repository.UserRepository;
import com.example.__mini_usr_management_app.utils.EmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
   private UserRepository userRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private EmailUtils emailUtils;
    @Override
    public String checkEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            return "UNIQUE";
        }
        return "DUPLICATE";
    }

    @Override
    public Map<Integer, String> getCountries() {
        List<Country> countries = countryRepository.findAll();
        Map<Integer , String> countryMap = new HashMap<>();
        countries.forEach(country -> {
            countryMap.put(country.getCountryId() , country.getCountryName());
        });
        return countryMap;
    }

    @Override
    public Map<Integer, String> getStates(Integer countryId) {
        List<State> states = stateRepository.findByCountryId(countryId);
        Map<Integer ,  String> statesMap = new HashMap<>();
        states.forEach(state -> {
            statesMap.put(state.getStateId() , state.getStateName());
        });
        return statesMap;
    }

    @Override
    public Map<Integer, String> getCities(Integer stateId) {
        List<City> cities = cityRepository.findByStateId(stateId);
        Map<Integer , String> citiesMap = new HashMap<>();
        cities.forEach(city -> {
            citiesMap.put(city.getCityId() , city.getCityName());
        });
        return citiesMap;
    }
    @Override
    public String registerUser(UserForm userForm) {
        //Coping Data from binding obj to entity obj
        User entity = new User();
        BeanUtils.copyProperties(userForm , entity);

        /** TODO:
         * 1. Generate and set random pwd
         * 2. Set Account Status as Locked
         */

        //1. Generate and set random pwd
         entity.setUserPwd(generateRandomPwd());

        //2. Set Account Status as Locked
        entity.setAccountStatus("LOCKED");

        //save method is requrid entity object that why i'm coping binding obj to entity obj
        userRepository.save(entity);

        /** TODO:
         * 1. Send Email to unlock Account
         * 2. Create email functionality like to, subject , body
         *      create a readEmailBody() method
         */
        String to = userForm.getEmail();
        String subject = "Registration Email";
        String body = readEmailBody("REG_EMAIL_BODY.txt" , entity);
        emailUtils.sendEmail(to , subject , body);

        return "User Account Created Successfully!";
    }

    @Override
    public String unlockAccount(UnlockAccountForm unlockAccountForm) {

        String email = unlockAccountForm.getEmail();
        User user = userRepository.findByEmail(email);

        /**
         * This condition is going to verify that the tempPwd is given by
         * user in unlock account form is valid or not
         */
        if(user != null && user.getUserPwd().equals(unlockAccountForm.getTempPwd())){
            user.setUserPwd(unlockAccountForm.getNewPwd());
            user.setAccountStatus("UNLOCK");
            userRepository.save(user);

            return "Account Unlocked!";
        }
        return "Invalid Temporary Password!";
    }

    @Override
    public String login(LoginForm loginForm) {

        User user = userRepository.findByEmailAndUserPwd(loginForm.getEmail(),
                loginForm.getPassword());

        if(user == null){
            return "Invalid Credentials!";
        }

        if(user.getAccountStatus().equals("LOCKED")){
            return "Account Locked!";
        }
        return "SUCCESS!";
    }

    @Override
    public String forgotPassword(String email) {

        User user = userRepository.findByEmail(email);

        if(user == null){
            return "No Account Found";
        }
        String subject = "Recover Password!";
        String body = readEmailBody("FORGOT_PWD_EMAIL_BODY.txt" , user);

        emailUtils.sendEmail(email, subject , body);

        return "Password sent to registered email!";
    }

    /**
     * Creating generateRandomPwd() method to generate Random
     * password of 6 digit length
     * @return: String
     */
    private String generateRandomPwd(){
        String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        StringBuilder sb = new StringBuilder();

        Random random = new Random();

        int pwdLength = 6;
        for(int i = 1 ; i <= pwdLength ; i++){
            int index = random.nextInt(text.length());
            sb.append(text.charAt(index));
        }
        return  sb.toString();
    }
    private  String readEmailBody(String filename , User user){
        StringBuilder sb = new StringBuilder();
        try (Stream<String> lines = Files.lines(Paths.get(filename))){
            lines.forEach(line -> {
                line = line.replace("${FNAME}" , user.getFirstName());
                line = line.replace("${LNAME}" , user.getLastName());
                line = line.replace("${TEMP_PWD}" , user.getUserPwd());
                line = line.replace("${EMAIL}" , user.getEmail());
                line = line.replace("${PWD}" , user.getUserPwd());
                sb.append(line);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
