package v1;

import v1.models.Attribute;
import v1.models.MemberAttribute;
import v1.models.NameAttribute;
import v1.models.User;
import v1.services.TrilloService;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        TrilloService trilloService = new TrilloService();
        Scanner sc = new Scanner(System.in);

        while(true){
            String[] commands = sc.nextLine().split(" ");
            switch (commands[0]) {
                case "Board":
                    switch (commands[1]) {
                        case "create":
                            trilloService.addBoard(commands[2]);
                            break;
                        default:
                            Attribute attribute = null;
                            if(commands[2].equals("Add_member")){
                                User user = new User(commands[3]);
                                attribute = new MemberAttribute("add_member", user);
                            } else {
                                attribute = new NameAttribute(commands[2], commands[3]);
                            }
                            trilloService.modifyBoard(commands[1], attribute);
                    }
                case "List":
                    switch (commands[1]) {
                        case "create":
                            trilloService.addChannel(commands[2], commands[3]);
                            break;
                        case "delete":
                            trilloService.deleteChannel(commands[2]);
                            break;
                        default:
                            Attribute attribute = null;
                            if(commands[2].equals("Add_member")){
                                User user = new User(commands[3]);
                                attribute = new MemberAttribute("add_member", user);
                            } else {
                                attribute = new NameAttribute(commands[2], commands[3]);
                            }
                            trilloService.modifyBoard(commands[1], attribute);
                    }
                case "Card":


                case "show":

                default:
            }
        }
    }
}
