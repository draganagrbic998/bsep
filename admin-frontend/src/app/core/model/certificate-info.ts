import { ExtensionsDTO } from './extensions-dto';

export class CertificateInfo {
    id: number;
    issuerAlias: string;
    alias: string;
    commonName: string;
    organization: string;
    organizationUnit: string;
    domain: string;
    country: string;
    email: string;
    template: string;
    revoked: boolean;
    numIssued: number;
    issued: CertificateInfo[];
    extensions: ExtensionsDTO = new ExtensionsDTO();
}
