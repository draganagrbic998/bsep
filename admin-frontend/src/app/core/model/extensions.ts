import {KeyPurposeId} from './key-purpose-id';
import {KeyUsageType} from './key-usage-type';

export class Extensions {
  extendedKeyUsage: KeyPurposeId[] | null = null;
  basicConstraints: boolean | null = null;
  keyUsage: KeyUsageType[] | null = null;
}

