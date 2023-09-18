package dev.flab.simpleweather.system;


public class Message {


    private StatusEnum status;
    private String message;
    private Object data;

    public Message(){

    }
    public Message(StatusEnum statusEnum, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }


    public void setStatus(StatusEnum status) {
        this.status = status;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }
    /*
       public StatusEnum getStatus() {
           return status;
       }

       public String getMessage() {
           return message;
       }
   */
    public Object getData() {
        return data;
    }

    public enum StatusEnum {
        OK(200, "OK"),
        BAD_REQUEST(400, "BAD_REQUEST"),
        NOT_FOUND(404, "NOT_FOUND"),
        INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR");

        int statusCode;
        String code;

        StatusEnum(int statusCode, String code) {
            this.statusCode = statusCode;
            this.code = code;
        }
    }
}
