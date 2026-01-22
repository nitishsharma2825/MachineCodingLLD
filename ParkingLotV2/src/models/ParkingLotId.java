package models;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public record ParkingLotId(String id) {
    private static final String ALPHABET = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final int LENGTH_OF_PROD_IDS = 5;
    public ParkingLotId {
        Objects.requireNonNull(id, "id can't be null");
        if (id.isEmpty()) {
            throw new IllegalArgumentException("id can't be empty");
        }
    }

    public static ParkingLotId randomParkingLotId() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        char[] chars = new char[LENGTH_OF_PROD_IDS];
        for (int i = 0; i < LENGTH_OF_PROD_IDS; i ++) {
            chars[i] = ALPHABET.charAt(random.nextInt(ALPHABET.length()));
        }
        return new ParkingLotId(new String(chars));
    }
}
