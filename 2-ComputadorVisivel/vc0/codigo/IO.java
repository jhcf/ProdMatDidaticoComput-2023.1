/**
 * IO.java - versao 1.1
 * Contains support methods for vc0's data entry.
 *
 * Copyright (c) 2002, Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
 * Uso irrestrito para fins educacionais e não lucrativos.
 * Free use only for educational and nonprofit purposes
*/

class IO {
  private IO() {} 
  private static final short MAX_INPUT_LEN = 256;
  private static byte[] bytes = new byte[MAX_INPUT_LEN];
  private static char[] caracts = new char[MAX_INPUT_LEN];
  private static boolean debug = true;
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
    int i = IO.readInt();
    println("O int digitado foi["+i+"]");
	
    String s = null;
    println("Digite um string e tecle <enter>");
    s = IO.readStr();
    println("O string digitado foi["+s+"]");
	
    println("Digite um string e tecle <enter>");
    s = IO.readStr();
    println("O string digitado foi["+s+"]");

    println("Digite um string e tecle <enter>");
    s = IO.readStr();
    println("O string digitado foi["+s+"]");

    println("Digite um inteiro e tecle <enter>");
    i = IO.readInt();
    println("O int digitado foi["+i+"]");
	
    println("Digite um short e tecle <enter>");
    short sh = IO.readShort();
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


