package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.util;

import lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.model.CustomerModel;
import java.util.List;

public class CustomerUtil {
    public static boolean isCustomerExists(List<CustomerModel> customers, String email) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(email));
    }

    public static CustomerModel findCustomerByName(List<CustomerModel> customers, String name) {
        return customers.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}