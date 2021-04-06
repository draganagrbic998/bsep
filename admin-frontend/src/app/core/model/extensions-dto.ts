import {KeyPurposeId} from './key-purpose-id';
import {KeyUsageType} from './key-usage-type';

export class ExtensionsDto {
  extendedKeyUsage: string[] | null = null;
  basicConstraints: boolean | null = null;
  keyUsage: number | null = null;
}
