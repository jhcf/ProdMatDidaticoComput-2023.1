class VarEntry extends SymbolTableEntry {
  VarEntry(String name, short value) {
    super(name, value);
  }
  short getOffset(short base) {
    return (short)(value+base);
  }
  String getType() { return  "Variable"; }
}
