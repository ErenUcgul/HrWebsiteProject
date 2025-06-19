package com.hrproject.hrwebsiteproject.model.enums;

public enum EEmploymentStatus {
    WORKING, FIRED, RESIGNATION, ON_HOLIDAY, UNDEFINED,ACTIVE,INACTIVE,DELETED
}
/*
Tüm liste dtosu
 Long userId;
 String identityNo;
 EPosition position;
 EEmploymentStatus employmentStatus;
 String firstName;
 String lastName;
 String email;

 Employee başına ayrıntı dtosu
 EmployeeEntity'den
 Long userId;
 String identityNo;
 LocalDate birthDate;
String address;
EPosition position;
LocalDate dateOfEmployment;
LocalDate dateOfTermination;
Double salary;
 EEmploymentStatus employmentStatus;
 String socialSecurityNumber;
 UserEntity'den
 String firstName;
 String lastName;
 String email;
 String password
 String phone;
String avatar;
Egender gender;
EUserState state;
 */