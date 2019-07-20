package com.example.dnjsr.smtalk.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserIdPattern {
    Pattern userIdPattern;
    Matcher userIdMatcher;
    String userId;

    public Boolean checkUserId(String userId){
        userIdPattern = Pattern.compile("(^[0-9a-zA-Z]{4,16}$)");  //0~9 소문자 대문자 a-z 4~16자리 만 가능
        userIdMatcher = userIdPattern.matcher(userId);
        return userIdMatcher.find();                    //일치하면 true 불일치하면 false리턴
    }
}
