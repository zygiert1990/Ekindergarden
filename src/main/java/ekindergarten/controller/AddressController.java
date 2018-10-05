package ekindergarten.controller;

import ekindergarten.domain.Address;
import ekindergarten.service.AddressService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping(value = "/add")
    public Address addAddress(@RequestBody @Valid Address address) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return addressService.addAddress(address, authentication.getName());
    }

    @PutMapping(value = "/update")
    public Address updateAddress(@RequestBody @Valid Address address) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return addressService.updateAddress(address, authentication.getName());
    }
}
