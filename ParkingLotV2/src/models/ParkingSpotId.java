package models;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public record ParkingSpotId(String id) {
    private static final String ALPHABET = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final int LENGTH_OF_PROD_IDS = 5;
    public ParkingSpotId {
        Objects.requireNonNull(id, "id can't be null");
        if (id.isEmpty()) {
            throw new IllegalArgumentException("id can't be empty");
        }
    }

    public static ParkingSpotId randomParkingSpotId() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        char[] chars = new char[LENGTH_OF_PROD_IDS];
        for (int i = 0; i < LENGTH_OF_PROD_IDS; i ++) {
            chars[i] = ALPHABET.charAt(random.nextInt(ALPHABET.length()));
        }
        return new ParkingSpotId(new String(chars));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ParkingSpotId that = (ParkingSpotId) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
