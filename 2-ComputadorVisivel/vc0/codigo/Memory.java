/**
 * Memory.java - versao 2
 * Models the vc0's RAM memory
 *
 * This code is part of the Computador Visivel - vc0
 * Copyright (c) 2002, Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
 * Todos os direitos reservados. All rights reserved.
 * Uso irrestrito para fins educacionais e não lucrativos.
 * Free use only for educational and nonprofit purposes
*/
class Memory {
  // a memory holds a sequence of values
  short[] values;
  // a memory holds a defined amount of values defined by its size 
  Memory(short size) {
    values = new short[size];
    for (int i = 0; i < size; i++) {
      values[i] = (short)0;
    } 
  }
  short getSize() {
    return (short) values.length;
  }
  short read(short position) {
    // a posição é convertida para um character porque não há posições de memória negativas
    return values[(char)position];
  }
  void write(short position, short _value) {
    // a posição é convertida para um character porque não há posições de memória negativas
    values[(char)position] = _value;
  }
  String dump() {
    int cellsPerLine = 8;
    int qtdLines = values.length / cellsPerLine;
    int cellCount = 0;
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < qtdLines; i++) {
      //buff.append("");
      for (int j = 0; j < cellsPerLine; j++) {
         int index = i*cellsPerLine+j;
         dumpCell(buf, index);
         cellCount++;
      }
      buf.append("\n");
    }
    while (cellCount < values.length) {
      dumpCell(buf, cellCount);      
      cellCount++;
    }
    buf.append("\n");
    return buf.toString();
  }
  void dumpCell(StringBuffer buf, int index) {
     buf.append("M["+index+"]=");
     String s = Formatter.dumpCell(values[index]);
     buf.append(s+"  ");
  }
  void print() {
    IO.println(dump());
  }
}