package models;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public record TicketId(String id) {
    private static final String ALPHABET = "23456789ABCD";
    private static final int ID_LENGTH = 5;
    public TicketId {
        Objects.requireNonNull(id, "id can't be null");
        if (id.isEmpty()) {
            throw new IllegalArgumentException("id can't be null");
        }
    }

    public static TicketId randomTicketId() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        char[] chars = new char[ID_LENGTH];
        for(int i=0; i < ID_LENGTH; i++){
            chars[i] = ALPHABET.charAt(random.nextInt(ID_LENGTH));
        }
        return new TicketId(new String(chars));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TicketId ticketId = (TicketId) object;
        return Objects.equals(id, ticketId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
