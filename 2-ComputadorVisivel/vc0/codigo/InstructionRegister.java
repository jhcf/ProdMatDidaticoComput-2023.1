/**
 * InstructionRegister.java - versao 1.1
 * Models a special kind of register, able to analize the instructions it held
 *
 * This code is part of the Computador Visivel - vc0
 * Copyright (c) 2002, Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
 * Todos os direitos reservados. All rights reserved.
 * Uso irrestrito para fins educacionais e não lucrativos.
 * Free use only for educational and nonprofit purposes
*/
class InstructionRegister extends Register {
  short getOpcode() {
    return (InstructionDescription.getOpcode(value));
  }
  short getOpArgument() {
    return (InstructionDescription.getOpArgument(value));
  }
  String dumpState() {
    return InstructionSet.toString(value);
  }
  void print() {
    IODrivers.println(dumpState());
  }
}
