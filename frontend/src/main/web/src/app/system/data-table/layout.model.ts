export class TableSettings {
  primaryKey: string;
  header?: string;
  type?: string;
  formatArgs?: string;
  alternativeKeys?: string[];
}

export class ColumnMap {
  primaryKey: string;
  header: string;
  type: string;
  formatArgs?: string;
  alternativeKeys?: string[];

  constructor(settings) {
    this.primaryKey = settings.primaryKey;
    this.header = settings.header;
    this.type = settings.type;
    this.formatArgs = settings.formatArgs;
    this.alternativeKeys = settings.alternativeKeys;
  }
}
