package resp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RespUtils {

    public static List<String> parseRespArray(BufferedReader in, String firstLine) throws IOException, IOException {
        List<String> elements = new ArrayList<>();
        int numElements = Integer.parseInt(firstLine.substring(1));
        for (int i = 0; i < numElements; i++) {
            String elementLengthLine = in.readLine();
            if (elementLengthLine == null || !elementLengthLine.startsWith("$")) {
                throw new IOException("Invalid RESP array format");
            }
            int elementLength = Integer.parseInt(elementLengthLine.substring(1));
            char[] elementChars = new char[elementLength];
            if (in.read(elementChars, 0, elementLength) != elementLength) {
                throw new IOException("Invalid RESP bulk string length");
            }
            in.readLine();
            elements.add(new String(elementChars));
        }
        return elements;
    }
}
