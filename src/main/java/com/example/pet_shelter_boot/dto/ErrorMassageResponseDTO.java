package com.example.pet_shelter_boot.dto;

import java.time.LocalDateTime;

public class ErrorMassageResponseDTO {
    private String massage;
    private String detailedMassage;
    private LocalDateTime dateTime;

    public ErrorMassageResponseDTO(String massage,
                                   String detailedMassage,
                                   LocalDateTime dateTime) {
        this.massage = massage;
        this.detailedMassage = detailedMassage;
        this.dateTime = dateTime;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getDetailedMassage() {
        return detailedMassage;
    }

    public void setDetailedMassage(String detailedMassage) {
        this.detailedMassage = detailedMassage;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
