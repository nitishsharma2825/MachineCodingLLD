package v1.services;

import v1.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrilloService {
    private List<Board> boards;
    private Map<String, Board> idBoardMapping;
    private Map<String, Board> channelIdBoardMappings;
    private Map<String, Channel> cardIdChannelMappings;
    public TrilloService() {
        this.boards = new ArrayList<>();
        idBoardMapping = new HashMap<>();
    }
    public String addBoard(String name){
        for(Board board: boards){
            if(board.getName().equals(name)){
                return null;
            }
        }
        Board createdBoard = new Board(name);
        boards.add(createdBoard);
        idBoardMapping.put(createdBoard.getId(), createdBoard);
        return createdBoard.getId();
    }

    public void deleteBoard(String id){
        Board deletedBoard = idBoardMapping.get(id);
        if(deletedBoard == null){
            System.out.println("Board: "+" "+id+ " does not exist");
            return;
        }
        boards.remove(deletedBoard);
        idBoardMapping.remove(id);
    }

    public void modifyBoard(String id, Attribute attribute) {
        Board modifyBoard = idBoardMapping.get(id);
        if(modifyBoard == null){
            System.out.println("Board: "+" "+id+ " does not exist");
            return;
        }

        switch (attribute.getFieldName().toLowerCase()) {
            case "name":
                modifyBoard.setName(((NameAttribute)attribute).getFieldValue());
                break;
            case "add_member":
                modifyBoard.getMembers().add(((MemberAttribute)attribute).getUser());
                break;
            case "remove_member":
                User user = ((MemberAttribute)attribute).getUser();
                modifyBoard.getMembers().remove(user);
                break;
            default:
                System.out.println("No such attribute in board");
        }
    }

    public void showBoard(String id){
        Board displayBoard = idBoardMapping.get(id);
        displayBoard.display();
    }

    public String addChannel(String boardId, String name){
        Board board = idBoardMapping.get(boardId);
        Channel channel = new Channel(name);
        if(board == null){
            System.out.println("Board: "+ boardId + " does not exist");
            return null;
        }
        board.getChannels().add(channel);
        channelIdBoardMappings.put(channel.getId(), board);
        return channel.getId();
    }

    public void modifyChannel(String channelId, Attribute attribute) {
        Board modifyBoard = channelIdBoardMappings.get(channelId);
        if(modifyBoard == null){
            System.out.println("Channel: "+" "+channelId+ " does not exist");
            return;
        }

        Channel channel = modifyBoard.getChannel(channelId);

        if (attribute.getFieldName().equalsIgnoreCase("name")) {
            channel.setName(((NameAttribute) attribute).getFieldValue());
        } else {
            System.out.println("No such attribute in board");
        }
    }

    public void deleteChannel(String channelId){
        Board modifyBoard = channelIdBoardMappings.get(channelId);
        if(modifyBoard == null){
            System.out.println("Board: "+" "+id+ " does not exist");
            return;
        }

        Channel channel = modifyBoard.getChannel(channelId);
        modifyBoard.getChannels().remove(channel);
        channelIdBoardMappings.remove(channelId);
    }

    public String createCard(String channelId, String name){
        Board board = channelIdBoardMappings.get(channelId);
        Card card = new Card(name);
        if(board == null){
            System.out.println("Board: "+ boardId + " does not exist");
            return null;
        }
        board.getChannel(channelId).getCards().add(card);
        cardIdChannelMappings.put(card.getId(), board.getChannel(channelId));
        return card.getId();
    }

    public void modifyCard(String cardId, Attribute attribute){
        Channel channel = cardIdChannelMappings.get(cardId);
        Board board = channelIdBoardMappings.get(channel.getId());
        Card card = channel.getCard(cardId);

        switch (attribute.getFieldName().equalsIgnoreCase()) {
            case "assign":
                User user = board.getMember(((NameAttribute)attribute).getFieldValue());
                card.setAssignedUser(user);
                break;
            case "unassign":
                card.setAssignedUser(new User());
                break;
            default:
                System.out.println("No such attribute in board");
        }
    }

    public void show(){
        for(Board board: boards){
            showBoard(board);
        }
    }

    public void showBoard(Board board){
        board.display();
    }

    public void showChannel(String channelId){
        Board board = channelIdBoardMappings.get(channelId);
        Channel channel = board.getChannel(channelId);
        channel.display();
    }

    public void showCard(String cardId){
        Channel channel = cardIdChannelMappings.get(cardId);
        Card card = channel.getCard(cardId);
        card.display();
    }

}
