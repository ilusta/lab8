package lab8.Essentials;

import java.io.Serializable;

public class Reply implements Serializable {

    private boolean isSuccessful = false;
    String message = "";
    private Object data = null;

    public Reply(boolean isSuccessful, Object data, String message){
        setSuccessful(isSuccessful);
        setData(data);
        setMessage(message);
    }

    public Reply(boolean isSuccessful, Object data){
        setSuccessful(isSuccessful);
        setData(data);
    }

    public Reply(boolean isSuccessful, String message){
        setSuccessful(isSuccessful);
        setMessage(message);
    }

    public Reply(boolean isSuccessful){
        setSuccessful(isSuccessful);
    }

    public void setSuccessful(boolean isSuccessful){
        this.isSuccessful=isSuccessful;
    }

    public void setData(Object data){
        this.data = data;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public boolean isSuccessful(){
        return  this.isSuccessful;
    }

    public Object getData(){
        return this.data;
    }

    public String getMessage(){
        return this.message;
    }
}
