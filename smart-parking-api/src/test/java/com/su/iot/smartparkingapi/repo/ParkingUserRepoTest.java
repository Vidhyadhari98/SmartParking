package com.su.iot.smartparkingapi.repo;

import com.su.iot.smartparkingapi.entity.ParkingUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ParkingUserRepoTest {

    @Autowired
    private ParkingUserRepo parkingUserRepo;

    @Test
    void save() {
        ParkingUser parkingUser = new ParkingUser();
        parkingUser.setUserName("user-name");
        parkingUser.setActive(true);

        final ParkingUser savedParkingUser = parkingUserRepo.save(parkingUser);

        final Optional<ParkingUser> parkingUserOptional = parkingUserRepo.findById(savedParkingUser.getParkingUserId());

        assertThat(parkingUserOptional).isNotEmpty();
    }

}