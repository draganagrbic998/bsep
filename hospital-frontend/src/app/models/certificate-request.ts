export class CertificateRequest {
  id: number;
  alias: string;
  issuerAlias: string;
  commonName: string;
  serialNumber: string;
  organization: string;
  organizationUnit: string;
  country: string;
  startDate: Date;
  endDate: Date;
  revoked: boolean;
  revocationReason: string;
  revocationDate: Date;
  isCA: string;
  email: string;
  template: string;
  numIssued: number;
  issued: CertificateInfo[];
  type: string;
}