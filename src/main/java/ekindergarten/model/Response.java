package ekindergarten.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response {

    public static String SUCCESS = "Success";

    String status;
    Object data;

    public static Response of(String status, Object data) {
        return new Response(status, data);
    }
}
