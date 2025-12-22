package org.onlineshop.in.rest.cart;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.onlinestore.application.port.in.cart.GetCartUseCase;
import org.onlinestore.model.cart.Cart;
import org.onlinestore.model.customer.CustomerId;

import static org.onlineshop.in.rest.common.CustomerIdParser.parseCustomerId;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class GetCartController {
    private final GetCartUseCase getCartUseCase;

    public GetCartController(GetCartUseCase getCartUseCase) {
        this.getCartUseCase = getCartUseCase;
    }
    @GET
    @Path("/{customerId}")
    public CartWebModel getCart(@PathParam("customerId") String customerIdString){
        CustomerId customerId = parseCustomerId(customerIdString);
        Cart cart = getCartUseCase.getCart(customerId);
        return CartWebModel.fromDomainModel(cart);
    }
}
