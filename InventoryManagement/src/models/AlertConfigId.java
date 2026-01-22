package models;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public record AlertConfigId(String id) {
    private static final String ALPHABETS = "123456789";
    private static final int ID_LENGTH = 5;
    public AlertConfigId {
        Objects.requireNonNull(id, "id can't be null");
        if (id.isEmpty()) {
            throw new IllegalArgumentException("Product Id can't be empty string");
        }
    }

    public static AlertConfigId randomProductId(){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        char[] chars = new char[ID_LENGTH];
        for(int i=0;i<ID_LENGTH;i++){
            chars[i] = ALPHABETS.charAt(random.nextInt(ALPHABETS.length()));
        }
        return new AlertConfigId(Arrays.toString(chars));
    }
}
