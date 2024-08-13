package com.example.UserService.annotations.validation.telephone;

import com.example.UserService.DTO.UserDTO;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Data;

public class PhoneRegionValidator implements ConstraintValidator<ValidPhoneRegion, UserDTO> {
    @Override
    public void initialize(ValidPhoneRegion constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDTO user, ConstraintValidatorContext context) {
        String phone = user.getTelephone();
        if (phone != null) {
            Telephone telephone = determineRegionByPhone(phone);
            String telephoneCode = telephone.getCountry();

            user.setTelephone(telephone.getNumber());
            user.setCountry(telephoneCode);

        }
        return true;

    }

    private Telephone determineRegionByPhone(String phone) {
        try {
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

            if(phone.charAt(0) == '8') {
                phone = "+7" + phone.substring(1);
            }
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phone, null);

            String totalTelephone = number.getCountryCode() + "" + number.getNationalNumber();
            String country = phoneNumberUtil.getRegionCodeForNumber(number);

            return new Telephone(totalTelephone, country);

        }   catch (NumberParseException e) {
            throw new RuntimeException(e);
        }

    }
}

@Data
class Telephone {
    private String number;
    private String country;

    public Telephone(String number, String country) {
        this.number = number;
        this.country = country;
    }


}