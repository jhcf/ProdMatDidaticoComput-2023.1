/**
 * IO.java - versao 1.1
 * Contains support methods for vc0's data entry.
 *
 * Copyright (c) 2002, Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
 * Uso irrestrito para fins educacionais e não lucrativos.
 * Free use only for educational and nonprofit purposes
*/
import java.io.FileReader;
import java.io.IOException;

class IODrivers {
  private IODrivers() {} 
  private static final short MAX_INPUT_LEN = 256;
  private static byte[] bytes = new byte[MAX_INPUT_LEN];
  private static char[] caracts = new char[MAX_INPUT_LEN];
  private static boolean debug = true;
  
  
  
  public static void error(String errorMessage) {
    println(errorMessage);
  }

      /**
     * Le um arquivo do sistema de arquivos, e retorna o seu conteudo como uma string
     */
  static int MAX_FILE_SIZE = 64000;
  // compiles a VisibleComputer0 program given its filename
  // returns the array of instructions as result

  public static String readFile(String fileName, boolean trace) {
    char[] vcmlCode = new char[MAX_FILE_SIZE];
    try {
      if (trace) println("BEGIN READING PROGRAM FROM FILE SYSTEM");
      // le o arquivo no disco
      FileReader fr = new FileReader(fileName);
      // joga todo o código de programa dentro do array de caracteres vcmlCode
      int bytesRead = fr.read(vcmlCode);
      // fecha o leitor de arquivos
      fr.close();
      
      if (trace) println("END   READING PROGRAM FROM FILE SYSTEM");
      // cria uma string com todo o código lido do disco
      String program = new String(vcmlCode, 0, bytesRead);
      if (trace) println(program);
      return program;
    } catch (IOException ioe) {
      error("Program file ["+fileName+"] not found.");
    }
    return null;
  }

  public static String readStr () 
  {
    int bytesLidos;
    try 
    {
      // os dados são capturados do teclado
      bytesLidos = System.in.read(bytes, 0, MAX_INPUT_LEN);
      if (debug) println("bytes lidos:"+bytesLidos);
    }
    catch (Exception e) 
    {
      System.out.println(e);
      // um string null é retornado em caso de erro 
      return null;
    }
    int bytesUteis = bytesLidos;
    // remove cr + lf - comportamento dependente de plataforma
    if (bytes[bytesLidos-1]=='\n'||bytes[bytesLidos-1]=='\r') {
          if (debug) println("ultimo byte removido");
          bytesUteis--; // despreza o último byte
          if (bytes[bytesLidos-2]=='\n'||bytes[bytesLidos-2]=='\r') {
             if (debug) println("penultimo byte removido");
             bytesUteis--; // despreza o penultimo byte
          } 
    }
    for (int i = 0; i < bytesUteis; i++)
    {
      if (debug) println( (int) bytes[i]);
      caracts[i] = (char) bytes[i];
    }
    String str = new String(caracts,0,bytesUteis);
    return str;
  }
  // retorna um inteiro lido do teclado
  public static int readInt() 
  {
    // usa a string retornada por readStr(), removendo os caracteres em branco
    String str = readStr().trim();
    // converte a string para inteiro
    return (Integer.parseInt(str));
  }

  public static short readShort() 
  {
    // le um inteiro no teclado e o converte para um short
    return (short) readInt();
  }   
 
  public static double readDouble()
  {
    // usa a string retornada por readStr(), removendo os caracteres em branco
    String str = readStr().trim();
    // converte a string para ponto flutuante de dupla precisão
    return (Double.parseDouble(str));
  }

  public static float readFloat()
  {
    // lê um double do teclado e o converte para float
    return (float)readDouble();
  }

  public static void readEnter () 
  {
    // le um string qualquer até encontrar o enter
    readStr();
  }
  public static void println(String str) 
  {
    // atalho para System.out.println
    System.out.println(str);
  }
  public static void println(Object obj) 
  {
    // atalho para System.out.println
    System.out.println(obj);
  }
  public static void print(Object obj) 
  {
    System.out.print(obj);
  }
  public static void println(int numero) 
  {
    System.out.println(numero);
  }
  public static void println(short numero) 
  {
    println((int)numero);
  }
  public static void print(int numero) 
  {
    System.out.print(numero);
  }
  public static void main(String[] args) 
  {
    println("Testando IO");

    println("Digite um inteiro e tecle <enter>");
    int i = IODrivers.readInt();
    println("O int digitado foi["+i+"]");
    
    String s = null;
    println("Digite um string e tecle <enter>");
    s = IODrivers.readStr();
    println("O string digitado foi["+s+"]");
    
    println("Digite um string e tecle <enter>");
    s = IODrivers.readStr();
    println("O string digitado foi["+s+"]");

    println("Digite um string e tecle <enter>");
    s = IODrivers.readStr();
    println("O string digitado foi["+s+"]");

    println("Digite um inteiro e tecle <enter>");
    i = IODrivers.readInt();
    println("O int digitado foi["+i+"]");
    
    println("Digite um short e tecle <enter>");
    short sh = IODrivers.readShort();
    println("O short digitado foi["+sh+"]");
    
    println("Digite um float e tecle <enter>");
    float f = readFloat();
    println("O float digitado foi["+f+"]");
    
    println("Digite um double e tecle <enter>");
    double d = readDouble();
    println("O double digitado foi["+d+"]");
    
    println("Tecle <enter> para encerrar o programa");
    readEnter();
    println("Fim do teste");
  }
}


