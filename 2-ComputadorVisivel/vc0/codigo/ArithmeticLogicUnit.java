/*
 * ArithmeticLogicUnit.java - versão 1.1
 * Model of a simple arithmetic logic unit, with offers supports to execution of 
 * instructions for integer addition, subtraction and comparison.  
 * It mimics data transformation, through its functional (combinational) "circuitry"
 *
 * This code is part of the Computador Visivel - vc0
 * Copyright (c) 2002, Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
 * Todos os direitos reservados. All rights reserved.
 * Uso irrestrito para fins educacionais e não lucrativos.
 * Free use only for educational and nonprofit purposes
 */
class ArithmeticLogicUnit {
  void print() {
    IODrivers.println("ALU: Operaçoes aritmeticas suportadas: add, sub, div, rem, mult, inv");
    IODrivers.println("ALU: Operaçoes logicas suportadas: equals, diff, greather, less");
  }
    
  short add(short  v1, short v2) {
    return (short) (v1 + v2);
  }
  short sub(short v1, short v2) {
    return (short) (v1 - v2);
  }
  short inv(short v1) {
    return (short) - v1;
  }
  short div(short v1, short v2) {
    return (short) (v1 / v2);
  }
  short rem(short v1, short v2) {
    return (short) (v1 % v2);
  }
  short mult(short v1, short v2) {
    return (short) (v1 * v2);
  }
  short equals(short v1, short v2) {
    if(v1 == v2) 
        return (short) 1;
    else 
        return (short)0;
  }
  short diff(short v1, short v2) {
    if (v1 != v2) 
        return (short) 1;
    else 
        return (short)0;
  }
  short greather(short v1, short v2) {
    if(v1 > v2) 
        return (short) 1;
    else 
        return (short)0;
  }
  short less(short v1, short v2) {
    if(v1 < v2) 
        return (short) 1;
    else 
        return (short)0;
  }
}