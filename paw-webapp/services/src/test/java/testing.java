import java.util.regex.Pattern;

public class testing {
    static final Pattern pattern = Pattern.compile(
            "^\\s?(\\+[0-9]{1,3}[ \\-]*)?" +
                    "([0-9]{1,4}[ \\-]*){2,4}\\s?"
    );

    public static void main(String[] args) {
        System.out.println(pattern.matcher("+54 9 11 3566 4292").matches());
        System.out.println(pattern.matcher("+54 11 3566 4292").matches());
        System.out.println(pattern.matcher("11 3566 4292").matches());
        System.out.println(pattern.matcher("3566 4292").matches());
        System.out.println(pattern.matcher("+734 12 444 3566 4292").matches());
        System.out.println(pattern.matcher("1772-444-3566-4292").matches());
    }

}
