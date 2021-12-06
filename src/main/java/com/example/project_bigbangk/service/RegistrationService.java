package com.example.project_bigbangk.service;
/*

@Author Philip Beeltje, Studentnummer: 500519452
*/

import com.example.project_bigbangk.model.RegistrationDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegistrationService {
    //dit kan heel makkelijk met een validation library, maar we doen het maar met de hand!
    private static final String EMAIL_REGEX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    //source:https://howtodoinjava.com/java/regex/java-regex-validate-email-address/
    private static final String PASSWORD_REGEX = "^(?=\\S+$).{8,}$";
    private static final String BSN_REGEX = "^[0-9]{9}$";
    public static final String POSTAL_REGEX = "^[1-9]{1}[0-9]{3} ?[A-Z]{2}$";
    public static final String COUNTRY_REGEX = "^[a-zA-Z]{3}$";

    public LocalDate convertedDateOfBirth;

    public void registerClient(RegistrationDTO registrationDTO){
        if(checkRegistrationInput(registrationDTO)){
            System.out.println(registrationDTO.getFirstName() + " " + registrationDTO.getLastName());
            //adress aanmaken, client aanmaken met embedden adress
            //opslaan in db
        }else{
            System.out.println("error.");
        }
    }

    //controleert input
    public boolean checkRegistrationInput(RegistrationDTO registrationDTO){
        List<String> inputNotNullList = Arrays.asList(registrationDTO.getFirstName(), registrationDTO.getLastName(),
                registrationDTO.getBsnNumber(), registrationDTO.getDateOfBirth(), registrationDTO.getEmail(), registrationDTO.getPassword(), registrationDTO.getStreet(),
                registrationDTO.getPostalCode(), registrationDTO.getCountry(), registrationDTO.getCity());

        for (String s : inputNotNullList) {
            if(s == null || s.length()==0){
                System.out.println("list");
                return false;}}
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        convertedDateOfBirth = LocalDate.parse(registrationDTO.getDateOfBirth(), formatter);

        if (!matchesRegex(registrationDTO.getEmail(), EMAIL_REGEX)){
            System.out.println("email");
            return false;}
        if (!matchesRegex(registrationDTO.getPassword(), PASSWORD_REGEX)){
            System.out.println("password");
            return false;}
        if(Period.between(convertedDateOfBirth, LocalDate.now()).getYears() < 18){
            System.out.println(LocalDate.now());
            System.out.println("age");
            return false;}
        if(!matchesRegex(registrationDTO.getBsnNumber(), BSN_REGEX)){
            System.out.println("bsn");
            return false;}
        if(!matchesRegex(registrationDTO.getPostalCode(), POSTAL_REGEX)){
            System.out.println("post");
            return false;}
        if(!matchesRegex(registrationDTO.getCountry(), COUNTRY_REGEX)){
            System.out.println("country");
            return false;}
        return true;
    }

    public boolean matchesRegex(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

//in postman, zet op post. http://localhost:8080/client/register. zet hem op body-raw-json. en dan dit in de body:
    //{
    //"email" : "philip.beeltje@gmail.com",
    //"password" : "password12345",
    //"firstName" : "Philip",
    //"insertion" : "",
    //"lastName" : "Beeltje",
    //"bsnNumber" : "123456789",
    //"dateofbirth" : "1986-01-07",
    //"postalCode" : "1241HL",
    //"street" : "HuisjesSteeg",
    //"number" : 8,
    //"city" : "Amstelredam",
    //"country" : "NLD"
    //}
}