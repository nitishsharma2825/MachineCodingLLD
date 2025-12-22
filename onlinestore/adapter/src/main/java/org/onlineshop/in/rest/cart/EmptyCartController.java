package org.onlineshop.in.rest.cart;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.onlinestore.application.port.in.cart.EmptyCartUseCase;
import org.onlinestore.model.customer.CustomerId;

import static org.onlineshop.in.rest.common.CustomerIdParser.parseCustomerId;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class EmptyCartController {
    private final EmptyCartUseCase emptyCartUseCase;

    public EmptyCartController(EmptyCartUseCase emptyCartUseCase) {
        this.emptyCartUseCase = emptyCartUseCase;
    }

    @DELETE
    @Path("/{customerId}")
    public void deleteCart(@PathParam("customerId") String customerIdString){
        CustomerId customerId = parseCustomerId(customerIdString);
        emptyCartUseCase.emptyCart(customerId);
    }
}
