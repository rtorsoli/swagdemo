package com.example.wallet.validator;

import com.example.wallet.model.enumeration.CoinEnum;
import com.example.wallet.model.request.WalletRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.*;


public class ValidAddressValidator implements ConstraintValidator<ValidAddress, WalletRequest> {
    private Pattern patternBtc;

    @Override
    public void initialize(ValidAddress annotation) {
        try {
            patternBtc = Pattern.compile("[13][a-km-zA-HJ-NP-Z1-9]{25,34}$|^[bB][cC]1[pPqQ][a-zA-Z0-9]{38,58}");

        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Given regex is invalid", e);
        }
    }
    
    @Override
    public boolean isValid(WalletRequest request, ConstraintValidatorContext context) {

        if (request == null) {
            return true;
        }

        if (!(request instanceof WalletRequest)) {
            throw new IllegalArgumentException("Illegal method signature, expected parameter of type WalletRequest.");
        }

        if (request.getAddress() == null || request.getCrypto() == null) {
            return false;
        }

        if (request.getCrypto() == CoinEnum.BTC) {
            Matcher m = patternBtc.matcher(request.getAddress());
            return m.matches();    
        } else {
            return false;
        }
    }
}
