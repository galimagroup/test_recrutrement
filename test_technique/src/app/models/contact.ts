export class Contact {
    id?: number; // L'ID est optionnel, car il est généré par le backend
    email!: string;
    message!: string;
    isHandled?: boolean; // Facultatif, car il sera probablement géré côté backend
  }
  