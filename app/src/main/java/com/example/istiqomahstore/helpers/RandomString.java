package com.example.istiqomahstore.helpers;

import java.util.Random;

public class RandomString {
    static String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    public static String token(){
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(16);
        for(int i=0;i<16;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
}
