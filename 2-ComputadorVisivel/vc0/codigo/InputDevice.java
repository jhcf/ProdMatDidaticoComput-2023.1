class InputDevice {
  // para fins de entrada de dados os valores são lidos com sinal (short)
  short read() {
    return (short) IODrivers.readShort();
  }
  
  void print() {
    IODrivers.println("INPUT_DEVICE - Receives data from user's console");
  }
}

