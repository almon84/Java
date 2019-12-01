
import java.io.*;

/* 
Писатель в файл с консоли
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bfrd = null;
        BufferedWriter dos = null;
        try {
            bfrd = new BufferedReader(new InputStreamReader(System.in));
            dos = new BufferedWriter(new FileWriter(new File(bfrd.readLine())));
            String s = bfrd.readLine();
            while(!s.equals("exit")) {
                dos.write(s + "\n");
                s = bfrd.readLine();
            }
            dos.write(s);
        } catch(Exception e) {

            e.printStackTrace();

        } finally {
            dos.close();
            bfrd.close();
        }
    }

}
