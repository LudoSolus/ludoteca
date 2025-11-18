export enum ETooltipBehavior {
  None = 0,
  Click = 1 << 0,       // Liga o 1º bit
  FocusIn = 1 << 1,     // Liga o 2º bit
  FocusOut = 1 << 2,    // Liga o 3º bit
  Hover = 1 << 3,       // Liga o 4º bit
} 