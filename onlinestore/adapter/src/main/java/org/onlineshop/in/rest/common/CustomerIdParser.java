package org.onlineshop.in.rest.common;

import jakarta.ws.rs.core.Response;
import org.onlinestore.model.customer.CustomerId;

import static org.onlineshop.in.rest.common.ControllerCommons.clientErrorException;

public class CustomerIdParser {
    private CustomerIdParser() {}
    public static CustomerId parseCustomerId(String string) {
        try {
            return new CustomerId(Integer.parseInt(string));
        } catch (IllegalArgumentException e) {
            throw clientErrorException(Response.Status.BAD_REQUEST, "Invalid 'customerId'");
        }
    }
}
