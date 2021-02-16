package sdc.primenumberchecker.utils;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

    /** Helpful function
     *
     * @param a
     * @param b
     * @param c
     * @return (a^b) % c
     */

    public long modPow(long a, long b, long c) {
        long result = 1;
        for (int i = 0; i < b; i++) {
            result *= a;
            result %= c;
        }
        return result % c;
    }
}
