/**
 * OutputDevice.java - versao 1.1
 * Models an output device.
 *
 * This code is part of the Computador Visivel - vc0
 * Copyright (c) 2002, Jorge H. C. Fernandes (jorge@dimap.ufrn.br)
 * Todos os direitos reservados. All rights reserved.
 * Uso irrestrito para fins educacionais e não lucrativos.
 * Free use only for educational and nonprofit purposes
*/
class OutputDevice {
  // para fins de saída de dados os valores são armazenados com sinal (short)
  void write(short value) {
    IO.println(value);
  }
}