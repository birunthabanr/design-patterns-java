// abstract interface for a smartcard
interface SmartCard {
    public boolean authenticate(String pin);
}

// concrete implmentation for a
// (simple - un-decorated) smartcard
class SimpleSmartCard implements SmartCard {
    public boolean authenticate(String pin) {
        // send the encrypted PIN to server to verify
        System.out.println("Sending encrypted value ["+pin+"] to server to verify");
        return true; // example
    }
}

// abstract decorator class for smartcard
abstract class SmartCardDecorator implements SmartCard {
    private final SmartCard smartCardToBeDecorated;
    public SmartCardDecorator(SmartCard smartCardToBeDecorated) {
        this.smartCardToBeDecorated = smartCardToBeDecorated;
    }
    public boolean authenticate(String pin) {
        // delegate the authentication task
        smartCardToBeDecorated.authenticate(pin);
        return true; // example
    }
}

// concrete decorator which add One Time Password
// capability
class OTPSmartCardDecorator extends SmartCardDecorator {
    public OTPSmartCardDecorator
    (SmartCard smartCardToBeDecorated) {
        super(smartCardToBeDecorated);
    }
    public boolean authenticate(String id) {
        // send the ID to server to send an OTP to mobile
        // send OTP to server to verify
        System.out.println("Sending ID ["+id+"] to server to get OTP to mobile");
        super.authenticate("otp at mobile of ownner with ID "+id);
        return true; // example
    }
}

// concrete decorator which add Two Factor Authentication
// capability
class TFASmartCardDecorator extends SmartCardDecorator {
    public TFASmartCardDecorator
    (SmartCard smartCardToBeDecorated) {
        super(smartCardToBeDecorated);
    }
    public boolean authenticate(String pin) {
        // send the encrypted PIN to server to send an OTP to mobile
        // send OTP to server to verify
        System.out.println("Sending PIN ["+pin+"] to server to get OTP to mobile");
        super.authenticate("otp at mobile of owner of PIN "+pin);
        return true; // example
    }
}

public class SmartCardPay {
    public static void main(String [] args) {

        SmartCard pincard = new SimpleSmartCard();
        pincard.authenticate("1234");

        SmartCard otpcard = new OTPSmartCardDecorator(pincard);
        otpcard.authenticate("abcd");
        SmartCard tfacard = new TFASmartCardDecorator(pincard);
        tfacard.authenticate("1234");
    }
}