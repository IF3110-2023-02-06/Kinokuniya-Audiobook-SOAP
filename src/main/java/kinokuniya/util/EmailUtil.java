package kinokuniya.util;
// package kinokuniya.util;

// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.net.HttpURLConnection;
// import java.net.URL;

// import com.fasterxml.jackson.core.JsonParseException;
// import com.fasterxml.jackson.databind.JsonMappingException;
// import com.fasterxml.jackson.databind.ObjectMapper;

// public class Admin {}
// public class EmailUtil {
//     public static final String URL = "http://localhost:3000/api/user/admin";

//     public static String getEmail() {
//         try {
//             StringBuilder result = new StringBuilder();
//             URL url = new URL(URL);
//             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//             conn.setRequestMethod("GET");
//             try (BufferedReader reader = new BufferedReader(
//                         new InputStreamReader(conn.getInputStream()))) {
//                 for (String line; (line = reader.readLine()) != null; ) {
//                     result.append(line);
//                 }
//             }
//             ObjectMapper mapper = new ObjectMapper();
//             Object res = mapper.readValue(result.toString(), null);

//             return result.toString();
//         } catch (Exception e) {
//             e.printStackTrace();
//             return "";
//         }
//     }
// }
