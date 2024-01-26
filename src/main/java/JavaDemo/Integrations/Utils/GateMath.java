package JavaDemo.Integrations.Utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class GateMath {

    public String roundingNumber(double number, String mode, int decimalPlaces) {
        String pattern = "#.";
        if(decimalPlaces==0) pattern = "#";
        for(int i=1;i<=decimalPlaces;i++){
            pattern=pattern+"#";
        }
        DecimalFormat df = new DecimalFormat("#.####");
        switch (mode.toLowerCase()){
            case "up" ->  df.setRoundingMode(RoundingMode.UP);
            case "down" ->  df.setRoundingMode(RoundingMode.DOWN);
            case "halfup" ->  df.setRoundingMode(RoundingMode.HALF_UP);
            case "halfdown" -> df.setRoundingMode(RoundingMode.HALF_DOWN);
            case "halfeven" ->  df.setRoundingMode(RoundingMode.HALF_EVEN);
            default -> throw new IllegalArgumentException("invalid rounding mode. Avaliable mode: up, down, halfup, halfdown, halfeven");
        }
        return df.format(number);
    }
}
