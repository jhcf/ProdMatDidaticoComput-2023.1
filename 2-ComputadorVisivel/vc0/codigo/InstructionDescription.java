/**
  * InstructionDescription.java 
  * Copyright (c) 2001, 2004 Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
  * Todos os direitos reservados. A rights reserved.
  * Uso irrestrito para fins educacionais e não lucrativos.
  * Free use only for educational and nonprofit purposes
  * 
  * Um InstructionDescription contém os atributos que definem uma instruçao no vc0
**/
// As instruções contém no máximo um operando
// Algumas instruções, como STOP ou HALT não contém operando
// instruções de máquina são armazenadas em 16 bits.
// os 6 bits de mais alta ordem são usados para armazenar o código do operador (opcode)
// os 10 bits de menor ordem são usados para armazenado o código do operando

class InstructionDescription {
  int opcode; // código do operando (até 6 bits são usados para indicar este código)
  String mnemonics; // conjunto de strings que podem ser usados como mnemônico de instrução
  String semantics; // pequeno texto que descreve a semântica da instrução
  boolean hasOperand; // true caso a instrução contenha um operando
  boolean instructionAddressesVar; // true caso a instrução enderece uma variável em memória
  InstructionDescription(int opcode_,String mnemonics_,String semantics_, boolean hasOperand_,boolean instructionAddressesVar_){
    this.opcode = opcode_;
    this.mnemonics = mnemonics_;
    this.semantics = semantics_;
    this.hasOperand = hasOperand_;
    this.instructionAddressesVar = instructionAddressesVar_;
  }
  // retorna verdadeiro caso o mnemônico recebido case com esta instrução 
  boolean matches(String mnemonic) {
    if (mnemonics.equalsIgnoreCase(mnemonic)) return true;
    return false;
  }


  // transforma um inteiro com sinal armazenado em 16 bits 
  // em um inteiro com sinal armazenado em 10 bits 
  static short from16To10Bits(short _16BitsValue) {
    //IO.println(_16BitsValue);
    if (_16BitsValue >= 0) return (short) (_16BitsValue&0x1FF); // 6 bits de alta ordem + bit sinal são truncados
    // o numero é negativo -> então 
    // inverte sinal
    //IO.println(Formatter.dumpBinState(_16BitsValue));
    short _10BitsValue = (short) -_16BitsValue;
    //IO.println(Formatter.dumpBinState(_10BitsValue));
    // trunca 
    _10BitsValue = (short)(_10BitsValue&0x3FF);
    //IO.println(Formatter.dumpBinState(_10BitsValue));
    // inverte sinal
    _10BitsValue = (short)(-_10BitsValue);
    //IO.println(Formatter.dumpBinState(_10BitsValue));
    // trunca 
    _10BitsValue = (short)(_10BitsValue&0x3FF);
    //IO.println(Formatter.dumpBinState(_10BitsValue));
    return _10BitsValue;
  }
    
  // transforma inteiro de 10 bits com sinal (armazenado em 16 bits)
  // em um short (inteiro de 16 bits com sinal) 
  static short from10To16Bits(short _10BitsValue) {
    //IO.println(_10BitsValue);
    //IO.println(Formatter.dumpBinState(_10BitsValue));
    if ((_10BitsValue & 0x200) == 0) {
        // se positivo retorna os 9 bits do valor
        return (short) (_10BitsValue&0x1FF); 
    }
    // o numero é negativo -> converte para positivo
    // subtrai de um
    short _16BitsValue = (short) (_10BitsValue - 1);
    //IO.println(Formatter.dumpBinState(_16BitsValue));
    // inverte bits, obtendo um positivo
    _16BitsValue = (short)(_16BitsValue^0x3FF);
    //IO.println(Formatter.dumpBinState(_16BitsValue));
    // inverte sinal, obtendo um negativo, e complementa com 1's
    _16BitsValue = (short)((-_16BitsValue)|0xFC00);
    //IO.println(Formatter.dumpBinState(_16BitsValue));
    return _16BitsValue;
  }
  // forma uma instrução de máquina a partir dos códigos numéricos do operador e do operando
  static short getMachineCode(short operatorCode, short operand) {
    short machineCode = (short)(((operatorCode&0x3f) << 10) | from16To10Bits(operand));
    return machineCode;
  }

  // obtém o código de operador a partir de uma instrução de máquina
  static short getOpcode(short machineCode) {
    return (short) (machineCode>>10);
  }

  // obtém, a representação numérica dos 10 bits que codificam o operando
  static short getOpArgument(short machineCode) {
    // 0x3ff (hexadecimal) == 0b1111111111 (binário)
    return from10To16Bits((short)(machineCode & 0x3ff));
  }

}
