//package com.project.rentcar.domain.dto;
//
//import com.project.rentcar.domain.entity.Car;
//import com.project.rentcar.domain.entity.Rent;
//import com.project.rentcar.domain.entity.User;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class RentDto {
//    private Long rentId;
//    private int userId;
//    private int carId;
//    private LocalDate rentDate;
//    private LocalDate returnDate;
//
//    public static Rent dtoToEntity(RentDto rentDto){
//        return Rent.builder()
//                .rentId(rentDto.getRentId())
//                .user(User.builder().userId(rentDto.getUserId()).build())
//                .car(Car.builder().carId(rentDto.getCarId()).build())
//                .rentDate(rentDto.getRentDate())
//                .returnDate(rentDto.getReturnDate())
//                .build();
//    }
//    public static RentDto EntityToDto(Rent rent){
//        return RentDto.builder()
//                .rentId(rent.getRentId())
//                .userId(rent.getUser().getUserId())
//                .carId(rent.getCar().getCarId())
//                .rentDate(rent.getRentDate())
//                .returnDate(rent.getReturnDate())
//                .build();
//    }
//
//}
