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
    private Map<String, Board> cardIdBoardMappings;
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
            System.out.println("Board: "+" "+id+ " does not exist");
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
        cardIdBoardMappings.put(card.getId(), board);
        return card.getId();
    }

}
