/**
 * SymbolTable.java
 *
 * Este código é parte do Computador Visivel - vc0
 * Copyright (c) 2004 Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
 * Todos os direitos reservados. All rights reserved.
 * Uso irrestrito para fins educacionais e não lucrativos.
 * Free use only for educational and nonprofit purposes
 * 
 * Implementa uma tabela de símbolos que pode ser usada por um compilador
*/

import java.util.Hashtable;
import java.util.Enumeration;

public class SymbolTable {
  /**
   * todos os dados de uma tabela de símbolos sao armazenados numa hashtable
   */
  Hashtable dictionary; 
  /*
   * Cria uma tabela de símbolos vazia
   */
  public SymbolTable() {
    dictionary = new Hashtable();
  }
  /*
   * Retorna true se uma tabela de símbolos contém um símbolo especificado
   */
  public boolean containsSymbol(String symbolName) {
    return dictionary.containsKey(symbolName);
  }
  /* 
   * insere um símbolo do tipo variável 
   */
  public void insertVar(String varName, short varIndex, boolean trace) {
    VarEntry var = new VarEntry(varName, varIndex);
    dictionary.put(varName, var);
    if (trace) {
      IODrivers.println("buildSymbolTable:Nova Variavel:["+varName+"] indice:["+varIndex+"]");
    }
  }
  /* 
   * insere um símbolo do tipo rótulo
   */
  public void insertLabel(String labelName, short labelAddr, boolean trace) {
    LabelEntry label = new LabelEntry(labelName, labelAddr);
    dictionary.put(labelName, label);
    if (trace) {
      IODrivers.println("buildSymbolTable:Novo Rotulo:["+labelName+"] endereço:["+labelAddr+"]");
    }
 
  }
  /*
   * recupera as informaçoes sobre um símbolo com um nome especifico
   */
  private SymbolTableEntry getSymbolEntry(String symbolName) {
    return (SymbolTableEntry) dictionary.get(symbolName);
  }
  /*
   * Recupera o deslocamento de um símbolo relativo a uma base específica
   */
  public short getSymbolOffset(String symbolName, short base) {
    SymbolTableEntry entry = (SymbolTableEntry) getSymbolEntry(symbolName);
    if (entry != null) {
        return entry.getOffset(base);
    } else {
        return -1;
    }
  }
  /*
   * imprime no console as informaçoes sobre todas as entradas de uma tabela de símbolos
   */
  public void print() {
    Enumeration e = dictionary.keys();
    StringBuffer buf = new StringBuffer();
    while (e.hasMoreElements()) {
      String symbolName = (String) e.nextElement();
      SymbolTableEntry entry = (SymbolTableEntry) getSymbolEntry(symbolName);
      buf.append(entry.dump());
    }
    IODrivers.print(buf.toString());
  }
}