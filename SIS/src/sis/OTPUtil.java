
package sis;
import java.util.Random;

public class OTPUtil {
    public static String generate() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
