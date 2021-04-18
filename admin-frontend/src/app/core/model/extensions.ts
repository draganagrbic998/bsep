import { KeyPurposeId } from './key-purpose-id';
import { KeyUsageType } from './key-usage-type';

export class Extensions {
  basicConstraints: boolean = null;
  keyUsage: KeyUsageType[] = null;
  extendedKeyUsage: KeyPurposeId[] = null;
}

