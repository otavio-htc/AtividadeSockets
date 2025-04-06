package comunicacao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Servidor {
    
    public static void main( String[] args ){
        
        try{
            
            ServerSocket ssServer  = 
                    new ServerSocket (8189);
            
            System.out.println("Aguardando conexao...");
            
            Socket sIncoming = ssServer.accept();
            System.out.println("Conectado!");
            
            InputStream inStream = 
                    sIncoming.getInputStream();
            Scanner in = new Scanner ( inStream, StandardCharsets.UTF_8);
            
            OutputStream outStream = 
                    sIncoming.getOutputStream();
            PrintWriter out = new PrintWriter( 
                        outStream, true,
                        StandardCharsets.UTF_8);
            
            boolean fim = false;
            
            while (!fim && in.hasNextLine()){
                
                String linha = in.nextLine();
                
                System.out.println("Cliente: " + linha );
                
                if ( linha.equalsIgnoreCase("tchau")){
                    
                    fim = true;
                    
                    out.println("Fim");
                    
                    break;
 
                }
 
                
                switch(linha){

                    case "1":

                        out.println("Ok! Requisicao 1 selecionada.");

                        linha = in.nextLine();

                        if(linha.contains("+")){    

                            out.println(somar(linha));

                        } else if ( linha.contains("-")){

                            out.println(subtrair(linha)); 

                        } else if ( linha.contains("*")){  

                            out.println(multiplicar(linha)); 

                        } else if ( linha.contains("/")){ 

                            if( dividir(linha) == -1 ){

                                out.println("Impossível!Divisão por zero.");

                            } else {

                                out.println(dividir(linha));

                            }
                        } else {
                            
                            out.println("Operacao invalida! Escolha uma "
                                    + "nova requisicao.");
                            
                            break;
                            
                        }
                        
                        break;


                    case "2":

                        out.println("Ok! Requisicao 2 selecionada");

                        linha = in.nextLine();

                        if (!linha.contains(";")){
                            
                           out.println("Escolha a requisicao novamente,"
                                   + " e digite no formato correto!");
                           
                           break;
                            
                        }
                        
                        String[] partes = linha.split(";");

                        String expressao = partes[0].trim();

                        String palavra = partes[1].trim();

                        int posicao = expressao.indexOf(palavra);

                        if( posicao != -1 ){  

                            out.println("A palavra inicia-se na posicao " 
                                    + posicao );

                        } else{   

                            out.println("A palavra nao foi encontrada."); 

                        }
                        
                        break;
                        

                    default:

                        out.println("Escolha entre \"1\" ou \"2\" ou "
                                + "digite \"Tchau\" para finalizar.");
                        
                        break;

                }
                
                    
            }
            
            sIncoming.close();
        
        } catch (IOException ex ){
            
            System.err.println("Não foi possível iniciar o servidor.");
            
        }
        
        
        
    }
    
    public static int somar(String expressao){
        
        String[] partes = expressao.split("\\+");
        
        int primeiro = Integer.parseInt(partes[0].trim());
        
        int segundo = Integer.parseInt(partes[1].trim());
        
        int soma = primeiro + segundo;
        
        return soma;
           
    }
    
    public static int subtrair( String expressao ){
        
        String[] partes = expressao.split("-");
        
        int primeiro = Integer.parseInt(partes[0].trim());
        
        int segundo = Integer.parseInt(partes[1].trim());
        
        int sub = primeiro - segundo;
        
        return sub;
          
    }
    
    public static int multiplicar( String expressao ){
        
        String[] partes = expressao.split("\\*");
        
        int primeiro = Integer.parseInt(partes[0].trim());
        
        int segundo = Integer.parseInt(partes[1].trim());
        
        int mult = primeiro * segundo;
        
        return mult;
        
    }
    
    public static double dividir( String expressao ){
        
        String[] partes = expressao.split("/");
        
        double primeiro = Double.parseDouble(partes[0].trim());
        
        double segundo = Double.parseDouble(partes[1].trim());
        
        double div = primeiro / segundo;
        
        if ( segundo == 0){
            
            return -1;
            
        }else {
            
           return div; 
            
        }
        
    }
    
    
}
