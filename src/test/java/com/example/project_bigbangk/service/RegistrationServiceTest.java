package com.example.project_bigbangk.service;

import com.example.project_bigbangk.model.RegistrationDTO;
import com.example.project_bigbangk.repository.RootRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {

    static RegistrationService registrationService;
    static HashService hashService = Mockito.mock(HashService.class);
    static IbanGeneratorService ibanGeneratorService = Mockito.mock(IbanGeneratorService.class);
    static RootRepository rootRepository = Mockito.mock(RootRepository.class);

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationService(hashService, ibanGeneratorService, rootRepository);
    }


    @Test
    void registerClient(){
        RegistrationDTO registrationDTO = new RegistrationDTO("henk@unicom.nl", "password1234345", "Henk", "de", "Kort",
                "123434546", "1950-01-01", "1111BN", "Straatie", 9, "Muiden", "NLD");

        String actual = registrationService.registerClient(registrationDTO);

        assertEquals("Registration Successful", actual);

        RegistrationDTO missingInput = new RegistrationDTO("", "password1234345", "Henk", "de", "Kort",
                "123434546", "1950-01-01", "1111BN", "Straatie", 9, "Muiden", "NLD");

        String actual2 = registrationService.registerClient(missingInput);

        assertEquals("Incorrect Input", actual2);
    }


    @Test
    void checkRegistrationInputOK() {
        RegistrationDTO registrationDTO = new RegistrationDTO("henk@unicom.nl", "password1234345", "Henk", "de", "Kort",
                "123434546", "1950-01-01", "1111BN", "Straatie", 9, "Muiden", "NLD");

        assertTrue(registrationService.checkRegistrationInput(registrationDTO));
    }

    @Test
    void checkRegistrationInputWrongEmail(){
        RegistrationDTO missingAt = new RegistrationDTO("henkunicom.nl", "password1234345", "Henk", "de", "Kort",
                "123434546", "1950-01-01", "1111BN", "Straatie", 9, "Muiden", "NLD");

        assertFalse(registrationService.checkRegistrationInput(missingAt));

        RegistrationDTO missingPoint = new RegistrationDTO("henk@unicomnl", "password1234345", "Henk", "de", "Kort",
                "123434546", "1950-01-01", "1111BN", "Straatie", 9, "Muiden", "NLD");

        assertFalse(registrationService.checkRegistrationInput(missingPoint));

        RegistrationDTO empty = new RegistrationDTO("", "password1234345", "Henk", "de", "Kort",
                "123434546", "1950-01-01", "1111BN", "Straatie", 9, "Muiden", "NLD");

        assertFalse(registrationService.checkRegistrationInput(empty));
    }

    @Test
    void checkRegistrationInputWrongPassword(){
        RegistrationDTO shortPW = new RegistrationDTO("henk@unicom.nl", "pas", "Henk", "de", "Kort",
                "123434546", "1950-01-01", "1111BN", "Straatie", 9, "Muiden", "NLD");

        assertFalse(registrationService.checkRegistrationInput(shortPW));

        RegistrationDTO noPW = new RegistrationDTO("henk@unicom.nl", "", "Henk", "de", "Kort",
                "123434546", "1950-01-01", "1111BN", "Straatie", 9, "Muiden", "NLD");

        assertFalse(registrationService.checkRegistrationInput(noPW));

        RegistrationDTO spaceInPw = new RegistrationDTO("henk@unicom.nl", "pas pas pas 123456789", "Henk", "de", "Kort",
                "123434546", "1950-01-01", "1111BN", "Straatie", 9, "Muiden", "NLD");

        assertFalse(registrationService.checkRegistrationInput(spaceInPw));
    }

    @Test
    void checkRegistrationInputWrongPostalCode(){
        RegistrationDTO postalOnlyNumbers = new RegistrationDTO("henk@unicom.nl", "password1234345", "Henk", "de", "Kort",
                "123434546", "1950-01-01", "111111", "Straatie", 9, "Muiden", "NLD");

        assertFalse(registrationService.checkRegistrationInput(postalOnlyNumbers));

        RegistrationDTO postalTooLong = new RegistrationDTO("henk@unicom.nl", "password1234345", "Henk", "de", "Kort",
                "123434546", "1950-01-01", "1111BNN", "Straatie", 9, "Muiden", "NLD");

        assertFalse(registrationService.checkRegistrationInput(postalTooLong));
    }

}