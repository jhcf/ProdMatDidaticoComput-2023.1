/**
 * Loader.java - versao 2
 * Models the vc0's program loader
 *
 * This code is part of the Computador Visivel - vc0
 * Copyright (c) 2002, Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
 * Todos os direitos reservados. All rights reserved.
 * Uso irrestrito para fins educacionais e não lucrativos.
 * Free use only for educational and nonprofit purposes
*/
import java.io.FileReader;
import java.io.IOException;

class Loader {
  public static void executeProgram(String program, boolean trace) throws Exception {
    short[] machineCode = Compiler.compile(program, trace);
    if (machineCode != null) {
      executeMachineCode(machineCode, trace);
      IO.println("");
      showSmallBanner(trace);
    } else {
      IO.println("Null machine code");
    }
  }

  static final short DEFAULT_MEMORY_SIZE = 128;

  public static void executeMachineCode(short[] machineCode, boolean trace) {
    // cria um computador 
    vc0 computer = new vc0(DEFAULT_MEMORY_SIZE);
    if (trace) IO.println("BEGIN LOADING PROGRAM IN MEMORY");
    // carrega o programa em memória
    computer.loadProgram(0, machineCode, trace);
    if (trace) IO.println("END   LOADING PROGRAM IN MEMORY");
    if (trace) IO.println(computer.dumpMemory());
    try {
      if (trace) IO.println("BEGIN PROGRAM EXECUTION");
      // manda o computador executar o programa
      computer.execute(trace);
      if (trace) IO.println("END   PROGRAM EXECUTION");
      if (trace) IO.println(computer.dump());
    } catch (Exception e) {
      // faz um dump de toda a informaçao em caso de problemas durante a execução
      diagnose(computer);
    }
  }
  
  public static void diagnose(vc0 computer) {
  	// collects some information about the error that occurred during execution
  	IO.println("EXECUTION FAILURE - DUMPING COMPUTER STATE");
  	IO.println(computer.dump());
  }

  static int MAX_FILE_SIZE = 64000;
  // compiles a VisibleComputer0 program given its filename
  // returns the array of instructions as result
  public static String readFile(String fileName, boolean trace) {
    char[] vcmlCode = new char[MAX_FILE_SIZE];
    try {
      if (trace) IO.println("BEGIN READING PROGRAM FROM FILE SYSTEM");
      // le o arquivo no disco
      FileReader fr = new FileReader(fileName);
      // joga todo o código de programa dentro do array de caracteres vcmlCode
      int bytesRead = fr.read(vcmlCode);
      // fecha o leitor de arquivos
      fr.close();
      
      if (trace) IO.println("END   READING PROGRAM FROM FILE SYSTEM");
      // cria uma string com todo o código lido do disco
      String program = new String(vcmlCode, 0, bytesRead);
      return program;
    } catch (IOException ioe) {
      error("Program file ["+fileName+"] not found.");
      return null;
    }
  }

  private static void showHelp(String optionName) {
    if (optionName.equals("i")) {
      showInstructionSetHelp();
    } else if (optionName.equals("a")) {
      showArchitectureHelp();
    } else {
      showUsageMessage();
    }
  }

  private static void showDefaultMessage() {
    showHeadBanner();
    showUsageMessage();
  }

  private static void showUsageMessage() {
    String mensagem = 
      "Usage Mode:															\n"+
      " java Loader -e <file-name>   Load and execute program stored in <file-name>\n"+
      " java Loader -ed <file-name>  Load and trace execution\n"+
      " java Loader -h <help-option> Print help information\n"+
      "    <help-option> = a    Print VC0's architecture	\n"+
      "    <help-option> = i    Print instruction set		\n"+
      "    <help-option> = p    Print sample program 1		\n"+
      "Example							:\n"+
      "  java Loader -e echo.a0					\n"+
      "  java Loader -ed multiply.txt				\n"+
      "  java Loader -h a					\n"+
      "  java Loader -h i					\n";
    IO.println(mensagem);
  }

  public static void showInstructionSetHelp() {
    IO.println(InstructionSet.dump());
  }

  private static void showArchitectureHelp() {
    IO.println(vc0.getArchitectureHelp());
  }

  private static void showHeadBanner() {
    showSmallBanner(true);
    String copyright = 
      "Copyright (c) 2001, 2004 - Jorge H. C. Fernandes (jhcf@dimap.ufrn.br)\n"+
      "All rights Reserved. Free for Educational and non profit use\n";
    IO.println(copyright);
  }
  private static void showSmallBanner(boolean trace) {
    if (trace) IO.println("\nLoader - vc0's Program Loader - build 1\n");
  }

  private static void error(String errorMessage) {
    IO.println(errorMessage);
  }
  // aqui é o ponto principal de execução do programa
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      showDefaultMessage();
    } else {
      String option = args[0];
      if (option.equals("-e") || option.equals("-ed") ) {
        boolean trace = option.equals("-ed");
        String fileName = args[1];
        String program = readFile(fileName, trace);
        if (program != null) {
          executeProgram(program, trace);
        } else {
          IO.println("Null program");
        }
      } else if (option.equals("-h")) {
        String optionName = args[1];
        showHelp(optionName);
      } else {
        showDefaultMessage();
      }
    }
  }
}