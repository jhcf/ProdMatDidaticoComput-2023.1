class UsandoCompilador {
  /*
   * Programa Java contido na classe chamada UsandoCompilador, 
   * que usar o Compilador do VisibleComputer, para compilar um programa escrito 
   * em VCML, que imprime o valor 333 no dispositivo de saída

   * O programa Java deve imprimir o valor de cada uma das instruções de máquina 
     geradas pelo compilador
   * O programa Java deve imprimir também o código VCML do programa desenvolvido
  */
  public static void main(String[] args) throws Exception {
    String programaVCML = "ACC 1024 STORE x WRITE x STOP"; // programa VCML
    char[] machineCode = Compiler.compile(programaVCML, false);
    // laco que vai visitar cada uma das instruções de máquinas geradas pelo compilador
    for (int i = 0; i < machineCode.length; i++) {
      // imprime um caracter por vez
      System.out.println(Value.dumpBinState(machineCode[i]));
    }
    System.out.println(programaVCML);
  }
}