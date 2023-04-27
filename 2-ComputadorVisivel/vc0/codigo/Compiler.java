/**
 * Compiler.java
 * Translates a program written in the languages VCML1, VCML2, VCML2
 * into an array of instructions 
 * The compilation is based on an implementation of a simple two steps compiler
 *
 * This code is part of the Computador Visivel - vc0
 * Copyright (c) 2002, Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
 * Todos os direitos reservados. All rights reserved.
 * Uso irrestrito para fins educacionais e não lucrativos.
 * Free use only for educational and nonprofit purposes
*/

import java.util.Vector;
import java.util.StringTokenizer;

public class Compiler {


  public static short[] compileFile(String fileName) {
      String program = IODrivers.readFile(fileName,true);
      try {
        short[] machineCode = Compiler.compile(program, true);
        return machineCode;
      } catch (Exception e) {
        System.out.println(e);
      }
      return null;
    }
  // compiles a VisibleComputer0 program given its contents in string format
  // returns the array of instructions as result
  public static short[] compile(String program, boolean trace) throws Exception {
    try {
      if (trace) IODrivers.println("BEGIN STRIP COMMENTS");
      String programWithoutComments = stripComments(program, trace);
      if (trace) IODrivers.println("END   STRIP COMMENTS");
      if (trace) IODrivers.println(programWithoutComments);
      if (trace) IODrivers.println("BEGIN BUILDING SYMBOL TABLE");
      SymbolTable symbolTable = buildSymbolTable(programWithoutComments, trace);
      if (trace) IODrivers.println("END   BUILDING SYMBOL TABLE");
      if (trace) IODrivers.println("BEGIN MACHINE CODE GENERATION");
      short[] code = buildCode(programWithoutComments, symbolTable, trace);
      if (trace) IODrivers.println("END   MACHINE CODE GENERATION");
      if (trace) printMachineCode(code);
      return code;
    } catch (Exception e) {
      e.printStackTrace();
      error("Compilation Error");
      return null;
    }
  }

    /**
     * Remove os comentarios de um programa
     */
  public static String stripComments(String program, boolean trace) {
    StringTokenizer parser = new StringTokenizer(program, "\n\r;",true);

    StringBuffer strippedProgram = new StringBuffer();
    while (parser.hasMoreTokens()) {
      // reads a label or instruction mnemonic
      String token = parser.nextToken();
      // check if it is a valid instruction name
      if (token.equals(";")) {// encontrou um comentario
          if (! parser.hasMoreTokens()) {
              break;
          }
          token = parser.nextToken();
          while (!(token.equals("\n") || token.equals("\r"))) {
              if (! parser.hasMoreTokens()) {
                  break;
              }
              token = parser.nextToken();          
          }
          if (token.equals("\n") || token.equals("\r")) {
               if (! parser.hasMoreTokens()) {
                  break;
              }
             token = parser.nextToken();          
          }
      } else {
        strippedProgram.append(token);
      }
    }
    return strippedProgram.toString();
  }


  // first pass into the code to build the symbol table
  public static SymbolTable buildSymbolTable(String program, boolean trace) {
    // counts the amount of instructions of the code
    short codeSize = 0;
    // counts the amount of declared vars into the code
    short varCount = 0;
    // creates an empty symboltable
    SymbolTable symbolTable = new SymbolTable();
    // creates a scanner to produce language tokens
    StringTokenizer parser = new StringTokenizer(program, "\n\t\r: ",false);

    while (parser.hasMoreTokens()) {
      // reads a label or instruction mnemonic
      String labelOrOpCode = parser.nextToken();
      // check if it is a valid instruction name
      if (InstructionSet.isValid(labelOrOpCode)) {
        // checks if the instruction uses an operand
        if (InstructionSet.hasOperand(labelOrOpCode)) {
          // read the operand
          String instructionArg = parser.nextToken();
          // checks if the operand has a symbolic name
          try {
            // try to convert the operand to an integer
            short value = (short) Integer.parseInt(instructionArg);
          } catch (NumberFormatException nfe) {
            // the operand could not be converted into an integer
            // it must be a symbolic operand

            // checks if the symbol has been used before
            if (!(symbolTable.containsSymbol(instructionArg))) {
              // declares the new symbol

              // checks if the symbol is a variable name or a
              // label name
              if (InstructionSet.addressesVar(labelOrOpCode)) {
                  // declares the variable and insert it into symbol table
                symbolTable.insertVar(instructionArg, varCount,trace);
                varCount++;
              }
            }
          }
        }
        // increments code size
        codeSize++;
      } else {
        // is a label - insert label into symbol table, displaced from
        // the begining of the program by "codeSize" value
        symbolTable.insertLabel(labelOrOpCode, codeSize, trace);
      }
    }
    // insert a last entry into symbol table with the value of code size
    symbolTable.insertLabel("$CODESIZE", codeSize, trace);
    return symbolTable;
  }

  // second pass into code, to generate final code
  public static short[] buildCode(String program, SymbolTable symbolTable, boolean trace)
                                                          throws Exception {
    Vector code = new Vector();
    // read code size calculated by buildTable
    short codeSize = symbolTable.getSymbolOffset("$CODESIZE", (short)0);
    StringTokenizer parser = new StringTokenizer(program, "\n\t\r: ",false);
    while (parser.hasMoreTokens()) {
      // reads label or instruction
      String labelOrOpCode = parser.nextToken();
      if (InstructionSet.isValid(labelOrOpCode)) {
        // is instruction
        if (InstructionSet.hasOperand(labelOrOpCode)) {
          // read instruction operand
          String instructionArg = parser.nextToken();
          // verify if operand is symbolic
          try {
            short value = (short) Integer.parseInt(instructionArg);
            // no symbolic operand
            // Lets generates code!
            Short s = new Short(InstructionSet.getMachineCode(labelOrOpCode, value));
            code.addElement(s);
          } catch (NumberFormatException nfe) {
            // as a symbolic operand
            // Lets generates code!

            short symbolOffset = symbolTable.getSymbolOffset(instructionArg, codeSize);
            code.addElement(new Short(InstructionSet.getMachineCode(labelOrOpCode, symbolOffset)));
          }
        } else {
          code.addElement(new Short(InstructionSet.getMachineCode(labelOrOpCode)));
        }
      }
    }
    if (code.size() != codeSize)
      error("Internal compiler Error: wrong codeSize.");

    short[] codeArray = new short[code.size()];
    for (int i = 0; i < code.size(); i++) {
      codeArray[i] = ((Short) code.elementAt(i)).shortValue();
    }
    return codeArray;
  }

  private static void error(String errorMessage) throws Exception {
    IODrivers.println(errorMessage);
    throw new Exception(errorMessage);
  }
  
  public static void printMachineCode(short[] machineCode) {
    for (int i = 0; i < machineCode.length; i++) {
      IODrivers.println("i["+i+"]=["+WordFormatter.dumpCell(machineCode[i])+"]");  
    }
  }
}
