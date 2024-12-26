package com.project.rentcar.domain.repository;

import com.project.rentcar.domain.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepositroy extends JpaRepository<Rent,Long> {


}
