package app;

import java.math.BigInteger; 
import java.security.MessageDigest;
import java.util.Base64; 
  
public class HashingPasswords { 
    public static String getSHA(String input) 
    { 
        try { 
  
        	//creo el digest, especificando el tipo de algoritmo.
            MessageDigest md = MessageDigest.getInstance("SHA1"); 
  
            //hago el digest byte a byte
            byte[] messageDigest = md.digest(input.getBytes()); 
            
            long t1 = System.nanoTime();
            String b64 = Base64.getEncoder().encodeToString(messageDigest);
            long t2 = System.nanoTime();
            System.out.println(t2-t1);
            
            t1 = System.nanoTime();
            //paso el digest a un numero  (el 1 es que es positivo)
            BigInteger no = new BigInteger(1, messageDigest); 
            
            t2 = System.nanoTime();
            System.out.println(t2-t1);
            return ""; 
        }  
  
        catch (Exception e) { 
            e.printStackTrace();
            return null;
        }
    } 
    
    
    public static void main(String[] args) {
		System.out.println(getSHA("kernel"));
	}
} 