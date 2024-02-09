package com.su.iot.smartparkingapi.repo;

import com.su.iot.smartparkingapi.entity.ParkingUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingUserRepo extends JpaRepository<ParkingUser, Long> {

    Optional<ParkingUser> findParkingUserByUserName(final String userName);
}
