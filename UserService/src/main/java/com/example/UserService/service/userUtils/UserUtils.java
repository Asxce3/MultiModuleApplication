package com.example.UserService.service.userUtils;

import com.example.UserService.exceptions.CountryCodeException;
import com.example.UserService.model.User;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserUtils {
    final String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    final String telephoneRegex = "^\\d{9,14}$";
    final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
    final String usernameRegex = "^[a-zA-Z0-9._]{3,16}$";

    public boolean validate(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public Telephone getCountryCode(String phoneNumber) {
        try {
            if (phoneNumber == null) {
                return null;
            }
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

            if(phoneNumber.charAt(0) == '8') {
                phoneNumber = "+7" + phoneNumber.substring(1);
            }
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, null);

            String totalTelephone = number.getCountryCode() + "" + number.getNationalNumber();
            String country = phoneNumberUtil.getRegionCodeForNumber(number);

            return new Telephone(totalTelephone, country);

        } catch (NumberParseException e) {
            e.printStackTrace();
            throw new CountryCodeException("Invalid phone number");
        }

    }

    public boolean validateUser(User user) {
        try {

            if(!validate(user.getUsername(), usernameRegex)){
                return false;
            }

            if(!validate(user.getPassword(), passwordRegex)){
                return false;
            }

            return validateSocietyFields(user);

        }   catch (Exception e) {
//            e.getStackTrace();
        }
        return false;

    }

    public boolean validateSocietyFields(User user) {
        Telephone telephone = getCountryCode(user.getTelephone());
        String userMail = user.getEmail();


        if(userMail == null && telephone == null){
            return false;
        }

        if(userMail != null && !validate(userMail, emailRegex)){
            return false;
        }

        if(telephone != null && !validate(telephone.getNumber(), telephoneRegex)){
            return false;
        }

        return true;
    }
    //!
    public void setTelephoneUser(User user){
        if(user.getTelephone() != null){
            Telephone telephoneAndCode = getCountryCode(user.getTelephone());

            String telephone = telephoneAndCode.getNumber();
            String country = telephoneAndCode.getCountry();
            if(country == null){
                throw new CountryCodeException("Invalid phone number");
            }

            user.setTelephone(telephone);
            user.setCountry(country);
        }

    }


}


class Telephone {
    private String number;
    private String country;
    public Telephone(String number, String country) {
        this.number = number;
        this.country = country;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
