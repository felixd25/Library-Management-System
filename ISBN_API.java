
//^You might need to remove that package above in order to run the program, it's just the folder this program was built in.
/*
ISBN_API.java
By Colin and Felix (Kin Ho)
January 20 2024
Java 8, Eclipse Neon 1.a (4.6.1)
Problem Definition – 
Input - 
Output – 
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;
 
public class ISBN_API {
 
    private static HttpURLConnection con;
 
    public static void main(String[] args) throws MalformedURLException, ProtocolException, IOException{
    	String p = getContent("9781541768529");
    	System.out.println(p);
    	System.out.println(getInfo(p, "title"));
    	System.out.println(getArrayInfo(p, "author_name"));
    }
    
    public static String getContent(String isbn) throws MalformedURLException,
            ProtocolException, IOException {
 
        String url = "https://openlibrary.org/search.json?q=" + isbn;
        StringBuilder content;
        
        try {
 
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();
            con.setRequestMethod("GET");
 
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
 
                String line;
                content = new StringBuilder();
 
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
        } finally {
            con.disconnect();
        }
        return content.toString();
    }
    
    public static String getInfo(String json, String property){
    	int position = json.indexOf("\"" + property + "\": ");
    	int rep = 0;
    	String info = "";
    	while(json.charAt(position + property.length() + 5 + rep) != '"'){
    		info += (json.charAt(position + property.length() + 5 + rep));
    		rep++;
    	}
    	return info;
    }
    
    public static String getArrayInfo(String jsonfull, String property){
    	String json = jsonfull.replaceAll("[\r\n]+", " ");
    	int position = json.indexOf("\"" + property + "\": [");
    	int rep = 0;
    	String info = "";
    	while(json.charAt(position+property.length() + 6 + rep) == ' '){
    		rep++;
    	}
    	rep++;
    	while(json.charAt(position + property.length() + 6 + rep) != '"'){
    		info += (json.charAt(position + property.length() + 6 + rep));
    		rep++;
    	}
    	return info;
    }
}