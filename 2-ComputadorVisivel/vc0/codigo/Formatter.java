/**
 * Value.java - versao 2
 * A value is a 16 bits value to be held into some place
 * of the visibleComputer (memory, registers or devices)
 * The "word" size of the computer is 16
 *
 * This code is part of the Computador Visivel - vc0
 * Copyright (c) 2003, Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
 * Todos os direitos reservados. All rights reserved.
 * Uso irrestrito para fins educacionais e não lucrativos.
 * Free use only for educational and nonprofit purposes
*/

public class Formatter {
  private Formatter() {};
  // creates a value given two values
  // note that the leftmost bits of each short are eliminated
  static int getHigherOrderByte(short value) {
    return ((value&0xff00)>>8);
  }
  static int getLowerOrderByte(short value) {
    return (value&0xff);
  }
  // assume that a zero value is a false answer
  static boolean isFalse(short value) {
    return (value == 0);
  }
  static String dumpBinState(short value) {
    StringBuffer buf = new StringBuffer();
    int x = 0x80;
    int mask;
    for (int i = 0; i < 8; i++) {
      mask = x >> i;
      int bitValue = getHigherOrderByte(value) & mask;
      if (bitValue > 0) {
        buf.append("1");
      } else {
        buf.append("0");
      }
    }
    buf.append(",");
    for (int i = 0; i < 8; i++) {
      mask = x >> i;
      int bitValue = getLowerOrderByte(value) & mask;
      if (bitValue > 0) {
        buf.append("1");
      } else {
        buf.append("0");
      }
    }
    return buf.toString();
  }
  static String dumpHexState(short value) {
    // seleciona cada conjunto de 4 bits do valor
    byte hex1 = (byte)((getHigherOrderByte(value) & 0xf0)>>4);
    byte hex2 = (byte)((getHigherOrderByte(value) & 0x0f));
    byte hex3 = (byte)((getLowerOrderByte(value) & 0xf0)>>4);
    byte hex4 = (byte)((getLowerOrderByte(value) & 0x0f));
    StringBuffer buff= new StringBuffer();
    // converte cada conjunto de 4 bits para um hexadecimal
    buff.append(toHex(hex1));
    buff.append(toHex(hex2));
    buff.append(toHex(hex3));
    buff.append(toHex(hex4));
    return buff.toString();
  }
  static private String toHex(byte hexByte) {
    // é preciso converter para inteiro por causa do case
    switch ((int)hexByte) {
      case 0: return "0";
      case 1: return "1";
      case 2: return "2";
      case 3: return "3";
      case 4: return "4";
      case 5: return "5";
      case 6: return "6";
      case 7: return "7";
      case 8: return "8";
      case 9: return "9";
      case 10: return "a";
      case 11: return "b";
      case 12: return "c";
      case 13: return "d";
      case 14: return "e";
      case 15: return "f";
    }
    return "?";
  }
  // cria uma representação textual de um valor qualquer
  static String dumpCell(short value) {
    StringBuffer buf = new StringBuffer();
    buf.append("0x"+dumpHexState(value));
    buf.append("=0d"+value);
    buf.append("=0b"+dumpBinState(value));
    buf.append("="+InstructionSet.toString(value));
    return buf.toString();
  }
}