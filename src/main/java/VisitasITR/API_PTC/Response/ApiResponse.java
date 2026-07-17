package VisitasITR.API_PTC.Response;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiResponse <T>{
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
