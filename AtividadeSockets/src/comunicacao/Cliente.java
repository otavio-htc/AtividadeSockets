package comunicacao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Cliente {
    
    public static void main( String[] args){
        
        try{
            
            Socket sClient = 
                    new Socket("127.0.0.1", 8189 );
            
            InputStream inStream =
                    sClient.getInputStream();
            Scanner in = new Scanner(inStream, StandardCharsets.UTF_8);
            
            
            OutputStream outStream =
                    sClient.getOutputStream();
            PrintWriter out = new PrintWriter(
                        outStream, true,
                        StandardCharsets.UTF_8);
            
            
            Scanner teclado = new Scanner(System.in);
            boolean fim = false;
            
            System.out.println("Requisicoes:\n"
                    + "Digite \"1\" para realizar "
                    + "uma operacao matematica.\n"
                    + "Digite \"2\" para realizar uma busca de palavra"
                    + " no formato \"frase;palavra\".");
            
            while ( !fim ){
                
                System.out.print("Digite: ");
                String mensagem = teclado.nextLine();
                
                out.println(mensagem);
                
                if (in.hasNextLine()){
                    
                    String linha = in.nextLine();
                    
                    System.out.println("Servidor: " + linha );
                    
                    if ( linha.equalsIgnoreCase("fim")){
                        
                        fim = true;
                        
                    }
                    
                }
                
                
            }
            
            sClient.close();
            
            
        } catch ( IOException ex ){
            
            System.err.println("Não foi possível conectar-se ao servidor!");
            
        }
        
        
        
    }
    
}
