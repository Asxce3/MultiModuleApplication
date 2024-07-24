package org.example.authservice.service.utils;

import org.example.authservice.models.Candidate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
    final String usernameRegex = "^[a-zA-Z0-9._]{3,16}$";

    private boolean checkField(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public boolean validateCandidate(Candidate candidate) {
        try {
            if(!checkField(candidate.getUsername(), usernameRegex)){
                return false;
            }

            if(!checkField(candidate.getPassword(), passwordRegex)){
                return false;
            }

            return true;

        }   catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

}
