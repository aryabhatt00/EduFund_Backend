package com.bankingprojectnew.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    // Store OTP with expiry time (e.g., 5 minutes)
    private static class OtpDetails {
        String otp;
        long expiryTimeMillis;

        OtpDetails(String otp, long expiryTimeMillis) {
            this.otp = otp;
            this.expiryTimeMillis = expiryTimeMillis;
        }
    }

    private final Map<String, OtpDetails> otpStore = new ConcurrentHashMap<>();

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(String email, String operation) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        long expiryMillis = System.currentTimeMillis() + (5 * 60 * 1000); // 5 minutes

        String key = email + "_" + operation;
        otpStore.put(key, new OtpDetails(otp, expiryMillis));

        //  Logging for debug
        System.out.println(">> Storing OTP key: " + key);
        System.out.println(">> OTP value: " + otp);

        //  Actually send the email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP for Transaction");
        message.setText("Your OTP is: " + otp + "\n\nNote: This OTP is valid for 5 minutes only.");
        mailSender.send(message);
    }

    public boolean verifyOtp(String email, String operation, String otp) {
        String key = email + "_" + operation;
        OtpDetails otpDetails = otpStore.get(key);

        if (otpDetails == null) return false;

        if (System.currentTimeMillis() > otpDetails.expiryTimeMillis) {
            otpStore.remove(key); // remove expired
            return false;
        }

        boolean match = otpDetails.otp.equals(otp);
        if (match) otpStore.remove(key); // OTP can be used only once

        return match;
    }
}
