/**
 * Register.java - versao 1.1
 * Models a general purpose register to be used in the CPU
 *
 * This code is part of the Computador Visivel - vc0
 * Copyright (c) 2002, Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
 * Todos os direitos reservados. All rights reserved.
 * Uso irrestrito para fins educacionais e não lucrativos.
 * Free use only for educational and nonprofit purposes
*/
class Register {
  // holds a signed value (short) formed by a sequence of 16 bits
  short value;
  // supports the replacemente of the value held in the register
  void write(short _value) {
    value = _value;
  }
  short read() {
    return value;
  }
  String dump() {
    return WordFormatter.dumpCell(value);
  }
  void print() {
    IODrivers.println(dump());
  }
}
