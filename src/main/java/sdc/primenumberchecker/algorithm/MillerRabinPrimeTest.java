package sdc.primenumberchecker.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sdc.primenumberchecker.utils.CommonUtils;

@Component
public class MillerRabinPrimeTest {

    static final Logger log = LoggerFactory.getLogger(MillerRabinPrimeTest.class);

    @Autowired
    CommonUtils utils;

    public boolean isNumberPrime(long number, int cycleCount) {
        //special cases
        if(number <= 1 || number == 4) {
            return false;
        } else if (number <= 3) {
            return true;
        }

        //check if number is odd
        if(number % 2 == 0) {
            return false;
        }

        //2^s * d = n-1
        //find d, gradually divide (n-1)/2
        long s = 0;
        long d = (number-1);
        while(d % 2 == 0) {
            d /= 2;
            s++;
        }

        log.trace("2^s * d = n-1: s = {}, d = {}", s, d);

        for(int i = 0; i < cycleCount ;i++) {
            if(!isPropablyPrime(number, s, d)) {
                return false;
            }
        }

        return true;
    }

    private boolean isPropablyPrime(long number, long s, long d) {
        //choose random number a from 2 to n-2
        long a = (long)(Math.random()*(number-2-2)+2);

        long x = utils.modPow(a, d, number);

        log.trace("x = a^d % n: a = {}, x = {}", a, x);

        //not composite
        if((x == 1) || (x == number-1)) {
            return true;
        }

        for(int r=1; r<s; r++) {
            x = utils.modPow(x, 2, number);
            //not composite
            if (x == number-1) {
                return true;
            //composite
            } else if (x == 1) {
                return false;
            }
        }

        //composite
        return false;
    }
}
