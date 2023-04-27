/**
 * VisibleComputer0.java - versao 1.1
 * The VisibleComputer0 is a software model of a very simple yet complete digital computer
 *
 * This code is part of the Computador Visivel - vc0
 * Copyright (c) 2002, Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
 * Todos os direitos reservados. All rights reserved.
 * Uso irrestrito para fins educacionais e não lucrativos.
 * Free use only for educational and nonprofit purposes
*/

public class vc0 {

  // all units of the computer are declared below
  
  // A computer contains an input device to receive data from the external medium
  InputDevice inputDevice;

  // A computer contains an output device to send data to the external medium
  OutputDevice outputDevice;
  
  // a computer contains a random access memory (RAM) to store and retrieve
  // data in efficient ways
  Memory memory;
  
  // This is the central unit of the Visible Computer
  CPU cpu;

  vc0(short memorySize) {
    inputDevice = new InputDevice();
    outputDevice = new OutputDevice();
    memory = new Memory(memorySize);
    cpu = new CPU();
    cpu.setBus(memory, inputDevice, outputDevice);
  }

  vc0 (InputDevice in, OutputDevice out, Memory mem, CPU cpu1) {
    this.inputDevice = in;
    this.outputDevice = out;
    this.memory = mem;
    this.cpu = cpu1;
    this.cpu.setBus(mem,in,out);
  }
  void loadProgram(int startPosition, short[] instructions, boolean trace) {
    if (trace) IODrivers.println("PROGRAM LENGTH = ["+instructions.length+"]");
    for (short i = 0; i < instructions.length; i++) {
      memory.write((short)(startPosition+i), instructions[i]);
    }
  }

  public String dump() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(cpu.dumpRegisters());
    buffer.append(dumpMemory());
    return buffer.toString();
  }
    
  public String dumpMemory() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("MEMORY DUMP = [\n"+memory.dump()+"]");
    return buffer.toString();
  }

  void execute(boolean trace) {
    cpu.execute(trace);
  }

  public static String getArchitectureHelp() {
    return 
      "******* VC0's MACRO ARCHITECTURE DESCRIPTION *****                    \n"+
      "Brief Architeture Description                                \n"+
      "    Small general purpose computer formed by a CPU, memory and i/o devices.        \n"+
      "    16 bits words.                                    \n"+
      "    instruction set composed by 10 instructions.                        \n"+
      "Architectural Elements                                    \n"+
      "1 - Central Processing Unit                                \n"+
      "    1.1 - ACC - General purpose register                            \n"+
      "    1.2 - IP (Instruction Pointer) - Points to the next instruction to be executed    \n"+
      "    1.3 - IR (Instruction Registre) - Stroes the next instruction being executed    \n"+
//      "    1.3 - SP (Stack Pointer) - Points to the top of the operand stack            \n"+
      "2 - I/O Devices                                        \n"+
      "    2.1 - INPUT_DEVICE - Receives data from user's console                \n"+
      "    2.2 - OUTPUT_DEVICE - Shows data in user's console                    \n"+
      "3 - Memory                                        \n"+
      "    3.1 - MEM - X words * 16 bits = x*16 bits memory                    \n"+
      "******* END VC0's ARCHITECTURE DESCRIPTION *******                    \n";
  }
  static void printArchitectureHelp() {
    IODrivers.println(getArchitectureHelp());
  }
  void print() {
    printArchitectureHelp();
    inputDevice.print();
    outputDevice.print();
    memory.print();
    cpu.print();
  }
}


