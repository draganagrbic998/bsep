export interface CertificateRequest {
  alias: string;
  commonName: string;
  organization: string;
  organizationUnit: string;
  country: string;
  email: string;
  template: string;
  path: string;
}
