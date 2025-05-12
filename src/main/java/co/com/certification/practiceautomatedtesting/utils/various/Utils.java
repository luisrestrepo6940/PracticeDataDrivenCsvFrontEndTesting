package co.com.certification.practiceautomatedtesting.utils.various;

import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor
public class Utils {
    public static int getRandomNumberAnInterval(int upperLimit) {
        return new Random().nextInt(upperLimit);
    }
}
