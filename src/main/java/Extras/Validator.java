package Extras;

import org.apache.commons.lang3.StringUtils;

public class Validator {

    public boolean isValid(String input) {
        return !StringUtils.isEmpty(input);
    }


}
