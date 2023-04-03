/**
  * InstructionSet.java 
  * Copyright (c) 2001, Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
  * Todos os direitos reservados. A rights reserved.
  * Uso irrestrito para fins educacionais e não lucrativos.
  * Free use only for educational and nonprofit purposes
  
  * Define o conjunto de instruções do vc0
  * 
**/

public class InstructionSet {
  // não existem objetos desta classe 
  private InstructionSet() {}

  // define qual o código numérico (opcode) de cada instrução
  public static final int NOP   = 0; // no operation
  public static final int STOP  = 1; // finaliza programa
  public static final int READ  = 2; // MEM[operand] <- INPUT
  public static final int WRITE = 3; // OUTPUT <- MEM[operand]
  public static final int LOAD  = 4; // ACCUMULATOR <- MEM[operando]
  public static final int STORE = 5; // MEM[operando] <- ACC
  public static final int ADD   = 6; // ACC <- ACC + MEM[operando]
  public static final int SUB   = 7; // ACC <- ACC - MEM[operando] 
  public static final int ACC   = 8; // ACC <- operand
  public static final int JUMP  = 9; // IP <- operand
  public static final int JNZ   = 10;// IF ACC != 0 then IP <- operand 
  public static final int MULT  = 11; // ACC <- ACC * MEM[operando]
  public static final int DIV   = 12; // ACC <- ACC / MEM[operando]
  public static final int REM   = 13; // ACC <- ACC % MEM[operando]
  public static final int INV   = 14; // ACC <- (-1 * ACC) 
  public static final int JGZ   = 15; // IF ACC > 0 then IP <- operand
  public static final int PUSH  = 16;// MEM[SP] <- MEM[operand]; SP <- SP + 1
  public static final int POP   = 17;// SP <- SP -1; MEM[operand] <- MEM[SP]
  public static final int CALL  = 18;// MEM[sp] <- IP; SP <- SP + 1
  public static final int RET   = 19;// SP <- SP -1; IP <- MEM[SP]

  // define qual a linguagem default interpretada pelo vc0.
  static final String defaultLanguage = "LA2"; // linguagem assembler 1 do vc0

  // indica qual o subconjunto de instruções que formam cada uma das linguagens interpretadas pelo vc0
  static int getQtyInstructions(String languageName) {
    if (languageName.equals("LA0")) return 4; // as instruções de 0 a 3 formam a linguagem assembler 0 do vc0
    if (languageName.equals("LA1")) return 11; // as instruções de 0 a 10 formam a linguagem assembler 1 do vc0
    if (languageName.equals("LA2")) return 16; // as instruções de 0 a 15 formam a linguagem assembler 2 do vc0
    if (languageName.equals("LA3")) return 20; // as instruções de 0 a 19 formam a linguagem assembler 3 do vc0
    return -1;
  }

  // contém o conjunto de informações sobre as instruções do vc0  
  static InstructionDescription[] instructionSet;
  static String actualLanguage = null;

  // este bloco é invocado por default, e faz com que o vc0 sempre interprete pelo menos uma 
  // linguagem computável mínima
  static {
    initializeInstructionSet(defaultLanguage);
  }

  // inicializa as estruturas de dados que definem o conjunto de instruções interpretáveis pelo vc0
  // o parâmetro string indica qual a linguagem que será interpretada pela máquina
  public static void initializeInstructionSet (String chosenLanguage) {
    // indica qual a linguagem correntemente interpretada pela máquina
    actualLanguage = chosenLanguage;
    // cria um vetor que contém todas as definições do conjunto de instruções
    int qtyInstructions = getQtyInstructions(chosenLanguage);
    if (qtyInstructions < 0) qtyInstructions = getQtyInstructions(defaultLanguage);
    instructionSet = new InstructionDescription[qtyInstructions];
    // cadastra cada uma das instruções que poderão ser interpretadas pelo vc0
    if (NOP < instructionSet.length) instructionSet[NOP]  = new InstructionDescription(NOP,  	"NOP", 	"'No Operation'"  ,		false, false);
    if (STOP < instructionSet.length) instructionSet[STOP] = new InstructionDescription(STOP, 	"STOP",	"'Halts program execution'", 	false,false);
    if (READ < instructionSet.length) instructionSet[READ] = new InstructionDescription(READ, 	"READ",	"MEM[operand] <- INPUT_DEVICE", true ,true);
    if (WRITE < instructionSet.length) instructionSet[WRITE]= new InstructionDescription(WRITE,	"WRITE","OUTPUT_DEVICE <- MEM[operand]",true ,true);
    if (LOAD < instructionSet.length) instructionSet[LOAD] = new InstructionDescription(LOAD, 	"LOAD",	"ACC <- MEM[operand]", 		true ,true);
    if (STORE < instructionSet.length) instructionSet[STORE]= new InstructionDescription(STORE,	"STORE","MEM[operando] <- ACC", 	true ,true);
    if (ADD < instructionSet.length) instructionSet[ADD]  = new InstructionDescription(ADD,  	"ADD",	"ACC <- ACC + MEM[operand]", 	true ,true);
    if (SUB < instructionSet.length) instructionSet[SUB]  = new InstructionDescription(SUB,  	"SUB", 	"ACC <- ACC - MEM[operand]", 	true ,true);
    if (ACC < instructionSet.length) instructionSet[ACC]  = new InstructionDescription(ACC,  	"ACC", 	"ACC <- operand",		true ,false);
    if (JUMP < instructionSet.length) instructionSet[JUMP] = new InstructionDescription(JUMP,	"JUMP",	"IP <- operand",		true ,false);
    if (JNZ < instructionSet.length) instructionSet[JNZ]  = new InstructionDescription(JNZ,  	"JNZ", 	"'IF' ACC != 0 'THEN' IP <- operand",true ,false);
    if (MULT < instructionSet.length) instructionSet[MULT]  = new InstructionDescription(MULT,	"MULT",	"ACC <- ACC * MEM[operando]", 	true, true); 
    if (DIV < instructionSet.length) instructionSet[DIV]  = new InstructionDescription(DIV,  	"DIV",	"ACC <- ACC / MEM[operando]", 	true, true); 
    if (REM < instructionSet.length) instructionSet[REM]  = new InstructionDescription(REM,  	"REM",	"ACC <- ACC % MEM[operando]", 	true, true); 
    if (INV < instructionSet.length) instructionSet[INV]  = new InstructionDescription(INV,  	"INV",	"ACC <- - ACC", 		false, false); 
    if (JGZ < instructionSet.length) instructionSet[JGZ]  = new InstructionDescription(JGZ,  	"JGZ",	"'IF' ACC > 0 'THEN' IP <- operand", true, false); 
    if (PUSH < instructionSet.length) instructionSet[PUSH] = new InstructionDescription(PUSH, 	"PUSH",	"SP <- SP - 1, MEM[SP] <- MEM[operand]; ", true ,true);
    if (POP < instructionSet.length) instructionSet[POP]  = new InstructionDescription(POP,  	"POP",	"MEM[SP] <- IP; SP <- SP + 1", 	true ,true);
    if (CALL < instructionSet.length) instructionSet[CALL] = new InstructionDescription(CALL, 	"CALL",	"", 				true ,true);
    if (RET < instructionSet.length) instructionSet[RET]  = new InstructionDescription(RET,  	"RET",	"", 				false,false); 

    // verifica a consistência do conjunto de instruções definidas
    for (int i = 0; i < instructionSet.length; i++) {
      InstructionDescription iDef = instructionSet[i];
      //verifica se o índice do opcode está consistente com a definição da instrução
      if ((iDef == null)||(iDef.opcode != i)){
        IO.println("Index mismatch in instruction whose opcode is ["+i+"]. "+
                           "Fatal error! ");
        System.exit(0);
      } 
      //verifica se não há duplicação de mnemônicos
      for (int j = i + 1; j < instructionSet.length; j++) {
          InstructionDescription iDef2 = instructionSet[j];
          if (iDef2.mnemonics.matches(iDef.mnemonics)) {
            IO.println("Duplicate mnemonic in instructions of opcodes ["+i+"] and ["+j+"]. "+
                           "Fatal error! ");
            System.exit(0);
          }
      }     
    }
  }

  // retorna o código opcode que corresponde a um mnemônico
  private static short getOpcode(String mnemonic) {
    for (short i = 0; i < instructionSet.length; i++) {
      InstructionDescription iDef = instructionSet[i];
      if (iDef.matches(mnemonic)) {
        return (short) i;
      }
    }
    return (short) -1;
  }

  // retorna true se o mnemônico é válido
  public static boolean isValid(String mnemonic) {
    return (getOpcode(mnemonic) >= 0);
  }

  // retorna true se uma instrução de um determinado mnemônico possui um operando 
  public static boolean hasOperand(String mnemonic) {
    if (! isValid(mnemonic)) return false;
    return instructionSet[getOpcode(mnemonic)].hasOperand;
  }

  // retorna true caso a o operando da instrução é usado para endereçar uma variável em memória
  // retorna false se a instrução não tem operando ou endereça uma instrução em memória
  public static boolean addressesVar(String mnemonic) {
    if (! isValid(mnemonic)) return false;
    if (! hasOperand(mnemonic)) return false;
    return instructionSet[getOpcode(mnemonic)].instructionAddressesVar;
  }

  public static void print() {
      IO.print(dump());
  }
  
  // imprime uma descrição do conjunto de instruções interpretadas pela máquina
  public static String dump() {
    StringBuffer buf = new StringBuffer();
    buf.append("\nVC0's CURRENT INSTRUCTION SET FOR LANGUAGE "+actualLanguage+"***** \n");
    for (short i = 0; i < instructionSet.length; i++) {
      InstructionDescription iDef = instructionSet[i];
      String name = iDef.mnemonics;
      String operandString = " \t\t";
      if (iDef.hasOperand) operandString = " <operand>\t";
      buf.append(name+operandString);
      buf.append(iDef.semantics+"\n");
    }
    buf.append("\nEND INSTRUCTION SET DESCRIPTION *******    \n");
    return buf.toString();
  }

  // forma uma instrução de máquina a partir dos mnemônico do operador e do código do operando
  static short getMachineCode(String mnemonic, short operand) {
    return InstructionDescription.getMachineCode((short)getOpcode(mnemonic), operand);
  }
  // forma uma instrução de máquina a partir dos mnemônico do operador (sem operando)
  static short getMachineCode(String mnemonic) {
    return getMachineCode(mnemonic, (short) 0);
  }

  // converts an instruction into its mnemonic format
  public static String opCodeToString(short machineCode) {
    if (InstructionDescription.getOpcode(machineCode) > instructionSet.length) return "???";
    short index = InstructionDescription.getOpcode(machineCode);
    if (index <0) {
      return "???";
    }
    return instructionSet[index].mnemonics;
  }
  
  // converts an instruction into its mnemonic format
  public static String toString(short machineCode) {
    return opCodeToString(machineCode) + " " + InstructionDescription.getOpArgument(machineCode);
  }
}
