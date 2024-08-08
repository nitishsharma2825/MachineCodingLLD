package v1.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Channel {
    private String id;
    private String name;
    private List<Card> cards;

    public Channel(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.cards = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void display(){
        System.out.println("Id: "+" "+id+"Name: "+name);
        if(!cards.isEmpty()){
            for(Card c: cards){
                c.display();
            }
        }
    }
    public Card getCard(String id){
        for(Card card: getCards()){
            if(card.getId().equals(id)){
                return card;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return Objects.equals(id, channel.id) && Objects.equals(name, channel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
