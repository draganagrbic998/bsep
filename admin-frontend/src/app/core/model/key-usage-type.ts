export class KeyUsageType {
  option: string;
  position: number;
  value: number;

  constructor(option: string, position: number) {
    this.option = option;
    this.position = position;
    this.value = Math.pow(2, this.position);
  }
}

