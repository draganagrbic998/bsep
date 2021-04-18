import { Template } from '../model/template';
import { id_kp_clientAuth, id_kp_serverAuth } from './key-purpose-ids';
import {
  cRLSign,
  digitalSignature,
  encipherOnly,
  keyAgreement,
  keyCertSign,
  keyEncipherment,
  nonRepudiation
} from './key-usages';

export const CUSTOM: Template = {
  label: 'Custom',
  enumValue: null,
  icon: 'pi pi-cog',
  extensions: {
    basicConstraints: null,
    keyUsage: null,
    extendedKeyUsage: null
  }
};

export const SUB_CA: Template = {
  label: 'CA',
  enumValue: 'SUB_CA',
  icon: 'pi pi-globe',
  extensions: {
    basicConstraints: true,
    keyUsage: [cRLSign, digitalSignature, keyCertSign],
    extendedKeyUsage: null
  }
};

export const TLS: Template = {
  label: 'TLS Server',
  enumValue: 'TLS',
  icon: 'pi pi-cloud',
  extensions: {
    basicConstraints: false,
    keyUsage: [nonRepudiation, digitalSignature, encipherOnly, keyEncipherment, keyAgreement],
    extendedKeyUsage: [id_kp_serverAuth]
  }
};

export const USER: Template = {
  label: 'User',
  enumValue: 'USER',
  icon: 'pi pi-user',
  extensions: {
    basicConstraints: false,
    keyUsage: [nonRepudiation, digitalSignature, encipherOnly, keyEncipherment],
    extendedKeyUsage: [id_kp_clientAuth]
  }
};

export const extensionTemplates = {
  SUB_CA,
  TLS,
  USER
};

export const getTemplate: (enumValue: string) => Template = (enumValue: string) => {
  if (!(enumValue in extensionTemplates)){
    return CUSTOM;
  }
  return extensionTemplates[enumValue];
};


