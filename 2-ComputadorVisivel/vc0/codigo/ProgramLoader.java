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

class ProgramLoader {
    
    /**
     * Comanda a execuçao de um programa passado como uma string, informando se a execuçao sera rastreada
     */
  public static void executeProgram(String program, short memorySize, boolean trace) throws Exception {
    short[] machineCode = Compiler.compile(program, trace);
    if (machineCode != null) {
      executeMachineCode(machineCode, memorySize, trace);
      IODrivers.println("");
      showSmallBanner(trace);
    } else {
      IODrivers.println("Null machine code");
    }
  }

    /**
     * Comanda a execuçao de um programa passado na forma de codigo de maquina, informando se a execuçao sera rastreada
     */
  public static void executeMachineCode(short[] machineCode, short memorySize, boolean trace) {
    // cria um computador 
    if  (machineCode.length == 0) {
       IODrivers.println("CODIGO DE MAQUINA VAZIO");
       return;
    }
    Computer computer = new Computer(memorySize);
    if (trace) IODrivers.println("BEGIN LOADING PROGRAM IN MEMORY");
    // carrega o programa em memória
    computer.loadProgram(0, machineCode, trace);
    if (trace) IODrivers.println("END   LOADING PROGRAM IN MEMORY");
    if (trace) IODrivers.println(computer.dumpMemory());
    try {
      if (trace) IODrivers.println("BEGIN PROGRAM EXECUTION");
      // manda o computador executar o programa
      computer.execute(trace);
      if (trace) IODrivers.println("END   PROGRAM EXECUTION");
      if (trace) IODrivers.println(computer.dump());
    } catch (Exception e) {
      // faz um dump de toda a informaçao em caso de problemas durante a execução
      diagnose(computer);
    }
  }
  
    /**
     * Apresenta o estado do computador, para fins de diagnostico
     */  
  public static void diagnose(Computer computer) {
      // collects some information about the error that occurred during execution
      IODrivers.println("EXECUTION FAILURE - DUMPING COMPUTER STATE");
      IODrivers.println(computer.dump());
  }

      /**
     * Apresenta algumas mensagens de auxilio
     */
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
      "Usage Mode:                                                            \n"+
      " java Loader -e <file-name> [<memorySize>]  Carrega na memoria e executa programa armazenado no arquivo <file-name> rm um computador com memoria de <memorySize> palavras\n"+
      " java Loader -ed <file-name> [<memorySize>] Carrega na memoria e depurar a execuçao de programa armazenado no arquivo <file-name> rm um computador com memoria de <memorySize> palavras\n"+
      " java Loader -h <help-option> IMprime mais informaçao de auxilio\n"+
      "    <help-option> = a    Imprime a arquitetura do vc0\n"+
      "    <help-option> = i    Imprime o conjunto de instruçoes\n"+
      "    <help-option> = p    Imprime um programa exemplo\n"+
      "Example                            :\n"+
      "  java Loader -e ../programas/ContagemRegressiva.montagem1\n"+
      "  java Loader -e ../programas/ContagemRegressiva.montagem1 128\n"+
      "  java Loader -ed ../programas/ContagemRegressiva.montagem1 128\n"+
      "  java Loader -h a\n"+
      "  java Loader -h i\n"+
      "  java Loader -h p\n";
    IODrivers.println(mensagem);
  }

  public static void showInstructionSetHelp() {
    IODrivers.println(InstructionSet.dump());
  }

  private static void showArchitectureHelp() {
    IODrivers.println(Computer.getArchitectureHelp());
  }

  private static void showHeadBanner() {
    showSmallBanner(true);
    String copyright = 
      "Copyright (c) 2001, 2004 - Jorge H. C. Fernandes (jhcf@dimap.ufrn.br)\n"+
      "All rights Reserved. Free for Educational and non profit use\n";
    IODrivers.println(copyright);
  }
  private static void showSmallBanner(boolean trace) {
    if (trace) IODrivers.println("\nLoader - vc0's Program Loader - build 1\n");
  }

  public static void executeFile(String fileName, short memorySize, boolean trace) throws Exception {
    String program = IODrivers.readFile(fileName, trace);
    if (program != null) {
      executeProgram(program, memorySize, trace);
    } else {
      IODrivers.println("Null program");
    }      
  }
  
  static final short DEFAULT_MEMORY_SIZE = 32;

  // aqui é o ponto principal de execução do programa
  public static void main(String[] args) throws Exception {
    if (args.length < 2) {
      showDefaultMessage(); 
    } else {
      String option = args[0];
      if (option.equals("-e") || option.equals("-ed") ) {
        boolean trace = option.equals("-ed");
        String fileName = args[1];
        String program = IODrivers.readFile(fileName, trace);
        if (program != null) {
          short memorySize = DEFAULT_MEMORY_SIZE;
          try {
              if (args.length == 3) {
                  String memorySizeString = args[2];
                  memorySize = Short.valueOf(memorySizeString);
                } 
            } catch (Exception e) {
                showDefaultMessage();
            }      
          executeProgram(program, memorySize, trace);
        } else {
          IODrivers.println("Null program");
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