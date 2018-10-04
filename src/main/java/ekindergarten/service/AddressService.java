package ekindergarten.service;

import ekindergarten.domain.Address;
import ekindergarten.domain.User;
import ekindergarten.repositories.AddressRepository;
import ekindergarten.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public Address addAddress(final Address address, final String email) throws RuntimeException {
        User user = userRepository.findByEmail(email);
        if (user.getAddress() != null)
            throw new RuntimeException("User has already add address");
        Address resultAddress = addressRepository
                .findAllByCityAndZipCodeAndHomeNumberAndFlatNumber(
                        address.getCity(), address.getZipCode(), address.getHomeNumber(), address.getFlatNumber());
        if (resultAddress == null) {
            Set<User> users = new HashSet<>();
            users.add(user);
            address.setUsers(users);
            user.setAddress(addressRepository.save(address));
            return address;
        }
        resultAddress.getUsers().add(user);
        user.setAddress(resultAddress);
        return resultAddress;
    }
}
