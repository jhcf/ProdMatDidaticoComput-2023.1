/**
 * CPU.java - versao 1.1
 * The CPU of the visible computer
 *
 * This code is part of the Computador Visivel - vc0
 * Copyright (c) 2002, Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
 * Todos os direitos reservados. All rights reserved.
 * Uso irrestrito para fins educacionais e não lucrativos.
 * Free use only for educational and nonprofit purposes
*/
public class CPU {

  // stores the instruction to be decoded
  InstructionRegister instructionRegister;
  // the cpu contains a general purpose register called accumulador.
  // It is the default register used for storing the result of the execution of instructions
  Register accumulator;
  // points to the next instruction to be fetched from the memory and further decoded 
  Register instructionPointer;
  // points to the logical top of the memory stack
//  Register stackPointer;
  // controls the execution of the program
  ControlUnit controlUnit;
  // computes transformation over data
  ArithmeticLogicUnit alu;
  
  CPU(Memory memory, InputDevice inputDevice, OutputDevice outputDevice) {
    controlUnit = new ControlUnit(this, memory, inputDevice, outputDevice);
    instructionRegister = new InstructionRegister();
    accumulator = new Register();
    instructionPointer = new Register();
//    stackPointer = new Register();
    alu = new ArithmeticLogicUnit();
  }

  public String dump() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(dumpRegisters());
    return buffer.toString();
  }
      
  public String dumpRegisters() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("ACC= ["+accumulator.dump()+"],");
    buffer.append("IP = ["+instructionPointer.dump()+"],");
//    buffer.append("SP = ["+stackPointer.dump()+"],");
    buffer.append("IR = ["+instructionRegister.dump()+"]");
    return buffer.toString();
  }

  void execute(boolean trace) {
    controlUnit.execute(trace);
  }
}
