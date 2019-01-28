package com.tdpteam.service.impl;

import com.tdpteam.service.helper.Constants;
import com.tdpteam.service.interf.PasswordService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Override
    public String generatePassword() {
        return RandomStringUtils.random(
                Constants.DEFAULT_PASSWORD_LENGTH,
                Constants.USE_LETTERS_FOR_DEFAULT_PASSWORD,
                Constants.USE_NUMBERS_FOR_DEFAULT_PASSWORD);
    }
}
