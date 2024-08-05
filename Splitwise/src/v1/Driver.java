package v1;

import v1.models.User;
import v1.services.SplitwiseService;
import java.util.List;
import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) {
        List<User> allUsers = new ArrayList<>();
        SplitwiseService splitwiseService = new SplitwiseService(allUsers);
        splitwiseService.startService();
    }
}
