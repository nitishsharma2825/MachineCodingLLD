package v1.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Board {
    private String id;
    private String name;
    private boolean isPublic;
    private String url;
    private List<User> members;
    private List<Channel> channels;

    public Board(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.members = new ArrayList<>();
        this.channels = new ArrayList<>();
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

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public User getMember(String name){
        for(User user: getMembers()){
            if(user.getName().equals(name)){
                return user;
            }
        }

        return null;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public void display(){
        System.out.print("ID: "+" "+id+" ");
        System.out.print("Name: "+" "+name+" ");
        System.out.print("Is Public: "+" "+(isPublic ? "PUBLIC ": "PRIVATE "));
        if(!members.isEmpty()){
            for(User u: members){
                u.display();
            }
        }
        if(!channels.isEmpty()){
            for(Channel ch: channels){
                ch.display();
            }
        }
        System.out.println();
    }

    public Channel getChannel(String channelId){
        for(Channel channel: getChannels()){
            if(channel.getId().equals(channelId)){
                return channel;
            }
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(id, board.id) && Objects.equals(name, board.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
