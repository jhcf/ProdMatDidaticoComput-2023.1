class InputDevice {
  // para fins de entrada de dados os valores são lidos com sinal (short)
  short read() {
    return (short) IO.readShort();
  }
}

