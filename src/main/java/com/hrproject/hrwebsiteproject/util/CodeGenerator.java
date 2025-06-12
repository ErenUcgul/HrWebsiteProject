package com.hrproject.hrwebsiteproject.util;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class CodeGenerator {

    public String generateActivationCode() {
        String uuid = UUID.randomUUID().toString();
        return Arrays.stream(uuid.split("-")).map(segment -> String.valueOf(segment.charAt(0)))
                .collect(Collectors.joining());
    }

    public static String generateResetPasswordCode() {
        String string = UUID.randomUUID().toString();

        String[] split = string.split("-");
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : split) {
            stringBuilder.append(s.charAt(0));
        }

        return stringBuilder.toString();
    }
}
