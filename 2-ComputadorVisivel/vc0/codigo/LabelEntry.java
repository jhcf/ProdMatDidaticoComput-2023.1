class LabelEntry extends SymbolTableEntry {
  LabelEntry(String name, short value) {
    super(name, value);
  }
  short getOffset(short base) {
    return value;
  }
  String getType() { return  "Label"; }
}

