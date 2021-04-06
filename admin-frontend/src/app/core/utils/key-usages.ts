import {KeyUsageType} from '../model/key-usage-type';

export const digitalSignature = new KeyUsageType('digitalSignature', 7);

export const nonRepudiation = new KeyUsageType('nonRepudiation', 6);

export const keyEncipherment = new KeyUsageType('keyEncipherment', 5);

export const dataEncipherment = new KeyUsageType('dataEncipherment', 4);

export const keyAgreement = new KeyUsageType('keyAgreement', 3);

export const keyCertSign = new KeyUsageType('keyCertSign', 2);

export const cRLSign = new KeyUsageType('cRLSign', 1);

export const encipherOnly = new KeyUsageType('encipherOnly', 0);

export const decipherOnly = new KeyUsageType('decipherOnly', 15);

export const keyUsages = {
  digitalSignature,
  nonRepudiation,
  keyEncipherment,
  dataEncipherment,
  keyAgreement,
  keyCertSign,
  cRLSign,
  encipherOnly,
  decipherOnly
};

export const getKeyUsages: (bytes: number) => KeyUsageType[] = (bytes: number) => {
  // tslint:disable-next-line:no-bitwise
  return Object.values(keyUsages).filter((usage: KeyUsageType) => !!((bytes >> usage.position) & 1));
};


