import java.util.Base64;

public class EncodingAndDecoding {
    public static void main(String[] args) {
        String encoded = Base64.getEncoder().encodeToString("Bye".getBytes());
        System.out.println(encoded);

        String encodedBye = "Qnll";
        String decoded = new String(Base64.getDecoder().decode(encodedBye));
        System.out.println(decoded);
    }
}
