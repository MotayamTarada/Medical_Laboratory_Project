//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//public class Mobile {
//    private static final String API_BASE_URL = "https://d7-verify.p.rapidapi.com/verify/v1/otp/generate";
//    private static final String API_KEY = "1f05af790dmshb3cfc2cfed064b2p194387jsn460557961f3f"; // Replace with your actual API key
//    private static final String API_HOST = "d7-verify.p.rapidapi.com";
//
//    public static void main(String[] args) {
//        String phoneNumber = "970599121954"; // Replace with the target phone number
//        String message = "Your OTP code is {code}."; // Example message format
//
//        try {
//            // Generate OTP
//            String generateOtpResponse = generateOtp(phoneNumber, message);
//            System.out.println("Generate OTP Response: " + generateOtpResponse);
//
//            // You can verify OTP here by replacing the {otp_id} and {otp_code} with actual values
//            // String otpId = "RECEIVED_OTP_ID";
//            // String otpCode = "RECEIVED_OTP_CODE";
//            // String verifyOtpResponse = verifyOtp(otpId, otpCode);
//            // System.out.println("Verify OTP Response: " + verifyOtpResponse);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Method to generate OTP
//    private static String generateOtp(String phoneNumber, String message) throws Exception {
//        String requestBody = String.format("{\"mobile\"%s\",\"message\"%s\"}", phoneNumber, message);
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(API_BASE_URL))
//                .header("Content-Type", "application/json")
//                .header("X-RapidAPI-Key", API_KEY)
//                .header("X-RapidAPI-Host", API_HOST)
//                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
//                .build();
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        return response.body();
//    }
//
//    // Method to verify OTP
//    private static String verifyOtp(String otpId, String otpCode) throws Exception {
//        String verifyOtpUrl = "https://d7-verify.p.rapidapi.com/verify/v1/otp/verify-otp";
//        String requestBody = String.format("{\"otp_id\"%s\",\"otp_code\"%s\"}", otpId, otpCode);
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(verifyOtpUrl))
//                .header("Content-Type", "application/json")
//                .header("X-RapidAPI-Key", API_KEY)
//                .header("X-RapidAPI-Host", API_HOST)
//                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
//                .build();
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        return response.body();
//    }
//}