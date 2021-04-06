import {Template} from '../model/template';
import {Extensions} from '../model/extensions';
import {
  cRLSign,
  digitalSignature,
  encipherOnly,
  keyAgreement,
  keyCertSign,
  keyEncipherment,
  nonRepudiation
} from './key-usages';
import {id_kp_clientAuth, id_kp_serverAuth} from './key-purpose-ids';

export const extensionTemplates: Template[] = [
  {
    label: 'CA',
    enumValue: 'SUB_CA',
    icon: 'pi pi-globe',
    extensions: {
      basicConstraints: true,
      keyUsage: [cRLSign, digitalSignature, keyCertSign],
      extendedKeyUsage: null
    }
  },
  {
    label: 'TLS Server',
    enumValue: 'TLS',
    icon: 'pi pi-cloud',
    extensions: {
      basicConstraints: false,
      keyUsage: [nonRepudiation, digitalSignature, encipherOnly, keyEncipherment, keyAgreement],
      extendedKeyUsage: [id_kp_serverAuth]
    }
  },
  {
    label: 'User',
    enumValue: 'USER',
    icon: 'pi pi-user',
    extensions: {
      basicConstraints: false,
      keyUsage: [nonRepudiation, digitalSignature, encipherOnly, keyEncipherment],
      extendedKeyUsage: [id_kp_clientAuth]
    }
  }
];
