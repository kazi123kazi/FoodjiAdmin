package admin.example.foodie.models;

import com.google.gson.annotations.SerializedName;

public class Payment {

    @SerializedName("method")
    String method;

    @SerializedName("status")
    String status;

    @SerializedName("total")
    int total;

    public Payment(String method, String status) {
        this.method = method;
        this.status = status;

    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public int getTotal() {
        return total;
    }
}
