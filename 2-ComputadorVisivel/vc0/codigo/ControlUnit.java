/**
  * ControlUnit.java 
  * Copyright (c) 2001, Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
  * Todos os direitos reservados. A rights reserved.
  * Uso irrestrito para fins educacionais e não lucrativos.
  * Free use only for educational and nonprofit purposes
  
  * Controls the execution of the program's instructions
  * 
**/

class ControlUnit {

  // the control unit need to acess the ALU and registers from the CPU
  CPU cpu;
  // the control unit needs access to the memory to execute some operations
  Memory memory;
  // the control unit needs access to the outputDevice to read data from the user
  InputDevice inputDevice;
  // the control unit needs access to the outputDevice to write data for the user
  OutputDevice outputDevice;

  ControlUnit(CPU cpu, Memory memory, InputDevice inputDevice, OutputDevice outputDevice) {
    this.memory = memory;
    this.inputDevice = inputDevice;
    this.outputDevice = outputDevice;
    this.cpu = cpu;
  }

  /* executa o ciclo de execução das instruçoes (do programa) */
  void execute(boolean trace) {
    cpu.instructionPointer.write((short)0); // armazena 0 no IP
    boolean stop = false; // verdadeiro quando uma instruÇao de stop é encontrada
    while (! stop) { // ciclo de execução do programa
      fetchNextInstruction();       // lê da memória a instruçao a ser decodificada
      incrementInstructionPointer();// incrementa o ponteiro de instruçoes
      stop = decodeInstruction(); // decodificar a instruçao
      if (trace) IO.println(cpu.dumpRegisters());
    }
  }

  void fetchNextInstruction() {
    short ipValue = cpu.instructionPointer.read();
    short instruction = memory.read(ipValue);
    cpu.instructionRegister.write(instruction);
  }

  void incrementInstructionPointer() {
    short ipValue = cpu.instructionPointer.read();
    short nextIpValue = cpu.alu.add(ipValue, (short)1);
    cpu.instructionPointer.write(nextIpValue);
  }

  boolean decodeInstruction() {
    short machineCode = cpu.instructionRegister.read();
    short opCode = InstructionDescription.getOpcode(machineCode);
    short opArgument = InstructionDescription.getOpArgument(machineCode);

    short value, valueAux, result, newSPValue;

    switch(opCode) {

      case InstructionSet.LOAD:
                  value = memory.read(opArgument);
                  cpu.accumulator.write(value);
                  return false;
      case InstructionSet.STORE:
                  value = cpu.accumulator.read();
                  memory.write(opArgument, value);
                  return false;
      case InstructionSet.ADD:
                  value = cpu.accumulator.read();
                  valueAux = memory.read(opArgument);
                  result = cpu.alu.add(value, valueAux);
                  cpu.accumulator.write(result);
                  return false;
      case InstructionSet.SUB:
                  value = cpu.accumulator.read();
                  valueAux = memory.read(opArgument);
                  result = cpu.alu.sub(value, valueAux);
                  cpu.accumulator.write(result);
                  return false;
      case InstructionSet.STOP:
                  return true;
      case InstructionSet.READ:
                  value = inputDevice.read();
                  memory.write(opArgument, value);
                  return false;
      case InstructionSet.WRITE:
                  value = memory.read(opArgument);
                  outputDevice.write(value);
                  return false;
      case InstructionSet.ACC:
                  cpu.accumulator.write(opArgument);
                  return false;
      case InstructionSet.JUMP:
                  cpu.instructionPointer.write(opArgument);
                  return false;
      case InstructionSet.JNZ:
                  value = cpu.accumulator.read();
                  valueAux = cpu.alu.equals(value, (short)0);
                  if (valueAux == 0) {
                    cpu.instructionPointer.write(opArgument);
                  }
                  return false;
/*
      case InstructionSet.MULT:
                  value = cpu.accumulator.read();
                  valueAux = memory.read(opArgument);
                  result = cpu.alu.mult(value, valueAux);
                  cpu.accumulator.write(result);
                  return false;
      case InstructionSet.DIV:
                  value = cpu.accumulator.read();
                  valueAux = memory.read(opArgument);
                  result = cpu.alu.div(value, valueAux);
                  cpu.accumulator.write(result);
                  return false;
      case InstructionSet.REM:
                  value = cpu.accumulator.read();
                  valueAux = memory.read(opArgument);
                  result = cpu.alu.rem(value, valueAux);
                  cpu.accumulator.write(result);
                  return false;
      case InstructionSet.INV:
                  value = cpu.accumulator.read();
                  result = cpu.alu.inv(value);
                  cpu.accumulator.write(result);
                  return false;
      case InstructionSet.JGZ:
                  value = cpu.accumulator.read();
                  valueAux = cpu.alu.greather(value, (short)0);
                  if (valueAux == 1) {
                    cpu.instructionPointer.write(opArgument);
                  }
                  return false;
**/
/**
      case InstructionSet.PUSH:
                  // le dado da memoria
                  valueAux = memory.read(opArgument);
                  // le valor corrente do stack pointer
                  value = cpu.stackPointer.read();
                  // grava valor no topo da pilha
                  memory.write(value,valueAux);
                  // incrementa stack pointer
                  newSPValue = cpu.alu.sub(value, (short)1);
                  cpu.stackPointer.write(newSPValue);
                  return false;
      case InstructionSet.POP:
                  // le valor corrente do stack pointer
                  value = cpu.stackPointer.read();
                  // diminui tamanho da pilha
                  newSPValue = cpu.alu.add(value, ((short)1));
                  cpu.stackPointer.write(newSPValue);
                  // retira elemento do topo da pilha
                  valueAux = memory.read(newSPValue);
                  // grava na posicao indicada
                  memory.write(opArgument, valueAux);
                  return false;
      case InstructionSet.CALL:
                  // le ponteiro de instrucao
                  valueAux = cpu.instructionPointer.read();
                  // le valor corrente do stack pointer
                  value = cpu.stackPointer.read();
                  // grava valor do IP no topo da pilha
                  memory.write(value,valueAux);
                  // incrementa stack pointer
                  newSPValue = cpu.alu.sub(value, ((short)1));
                  cpu.stackPointer.write(newSPValue);
                  // desvia para o endereco chamado
                  cpu.instructionPointer.write(opArgument);
                  return false;
      case InstructionSet.RET:
                  // le valor corrente do stack pointer
                  value = cpu.stackPointer.read();
                  // diminui tamanho da pilha
                  newSPValue = cpu.alu.add(value, ((short)1));
                  cpu.stackPointer.write(newSPValue);
                  // retira elemento do topo da pilha
                  valueAux = memory.read(newSPValue);
                  // grava o valor de retorno que estava no topo da pilha para o ponteiro de instrucao
                  cpu.instructionPointer.write(valueAux);
                  return false;

      case InstructionSet.NOP:
                  return false;
*/
     }
     return true;
   }
}
