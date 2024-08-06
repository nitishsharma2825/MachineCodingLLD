package v2;

import v1.models.User;
import v1.services.SplitwiseService;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        List<User> allUsers = new ArrayList<>();
        SplitwiseService splitwiseService = new SplitwiseService(allUsers);
        splitwiseService.startService();
    }
}
