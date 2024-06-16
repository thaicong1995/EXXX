package com.example.DepartmentManagement.Dto;

public class DepartmentDto {
    private String name;
    private String roomNumber;

    public DepartmentDto() {
    }

    public DepartmentDto( String name, String roomNumber) {
        this.name = name;
        this.roomNumber = roomNumber;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
