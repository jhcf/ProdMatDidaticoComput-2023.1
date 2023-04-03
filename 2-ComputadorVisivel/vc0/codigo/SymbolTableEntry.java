abstract class SymbolTableEntry {
  String name;
  short value;
  SymbolTableEntry(String name, short _value) {
    value = _value;
  }
  abstract short getOffset(short base);
  abstract String getType();
  public String dump() { return "Name=["+name+"]\tValue=["+value+"]\tType=["+getType()+"]\n";}

}

