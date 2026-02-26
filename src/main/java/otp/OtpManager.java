package otp;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class OtpManager {

    private static class OtpData {
        String otp;
        LocalDateTime expiry;
    }

    private static final ConcurrentHashMap<String, OtpData> otpStore = new ConcurrentHashMap<>();
    public static void storeOtp(String email, String otp) {
        OtpData data = new OtpData();
        data.otp = otp;
        data.expiry = LocalDateTime.now().plusMinutes(5);
        otpStore.put(email, data);
    }
    public static boolean validateOtp(String email, String userOtp) {
        OtpData data = otpStore.get(email);

        if (data == null) return false;
        if (LocalDateTime.now().isAfter(data.expiry)) {
            otpStore.remove(email);
            return false;
        }

        boolean isValid = data.otp.equals(userOtp);

        if (isValid) {
            otpStore.remove(email); // Remove after success
        }

        return isValid;
    }


    /*
    String userEmail = "user@example.com";


    // Step 1: Generate OTP
        String otp = OtpGenerator.generateOtp();

        // Step 2: Store OTP
        OtpManager.storeOtp(userEmail, otp);

        // Step 3: Send Email
        EmailService.sendOtpEmail(userEmail, otp);

        // Later: Validate
        boolean valid = OtpManager.validateOtp(userEmail, "123456");

        System.out.println("OTP Valid: " + valid);
     */

}
