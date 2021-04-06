import {KeyUsageType} from '../model/key-usage-type';

export const digitalSignature: KeyUsageType = {
  option: 'digitalSignature',
  value: 128
};

export const nonRepudiation: KeyUsageType = {
  option: 'nonRepudiation',
  value: 64
};

export const keyEncipherment: KeyUsageType = {
  option: 'keyEncipherment',
  value: 32
};

export const dataEncipherment: KeyUsageType = {
  option: 'dataEncipherment',
  value: 16
};

export const keyAgreement: KeyUsageType = {
  option: 'keyAgreement',
  value: 8
};

export const keyCertSign: KeyUsageType = {
  option: 'keyCertSign',
  value: 4
};

export const cRLSign: KeyUsageType = {
  option: 'cRLSign',
  value: 2
};

export const encipherOnly: KeyUsageType = {
  option: 'encipherOnly',
  value: 1
};

export const decipherOnly: KeyUsageType = {
  option: 'decipherOnly',
  value: 32768
};

export const keyUsages: KeyUsageType[] = [
  digitalSignature,
  nonRepudiation,
  keyEncipherment,
  dataEncipherment,
  keyAgreement,
  keyCertSign,
  cRLSign,
  encipherOnly,
  decipherOnly
];


