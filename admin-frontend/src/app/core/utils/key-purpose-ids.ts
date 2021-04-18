import { KeyPurposeId } from '../model/key-purpose-id';

export const id_kp_serverAuth: KeyPurposeId = {
  option: 'id_kp_serverAuth',
  value: '1'
};

export const id_kp_clientAuth: KeyPurposeId = {
  option: 'id_kp_clientAuth',
  value: '2'
};

export const id_kp_codeSigning: KeyPurposeId = {
  option: 'id_kp_codeSigning',
  value: '3'
};

export const id_kp_emailProtection: KeyPurposeId = {
  option: 'id_kp_emailProtection',
  value: '4'
};

export const id_kp_ipsecEndSystem: KeyPurposeId = {
  option: 'id_kp_ipsecEndSystem',
  value: '5'
};

export const id_kp_ipsecTunnel: KeyPurposeId = {
  option: 'id_kp_ipsecTunnel',
  value: '6'
};

export const id_kp_ipsecUser: KeyPurposeId = {
  option: 'id_kp_ipsecUser',
  value: '7'
};

export const id_kp_timeStamping: KeyPurposeId = {
  option: 'id_kp_timeStamping',
  value: '8'
};

export const id_kp_OCSPSigning: KeyPurposeId = {
  option: 'id_kp_OCSPSigning',
  value: '9'
};

export const id_kp_dvcs: KeyPurposeId = {
  option: 'id_kp_dvcs',
  value: '10'
};

export const id_kp_sbgpCertAAServerAuth: KeyPurposeId = {
  option: 'id_kp_sbgpCertAAServerAuth',
  value: '11'
};

export const id_kp_scvp_responder: KeyPurposeId = {
  option: 'id_kp_scvp_responder',
  value: '12'
};

export const id_kp_eapOverPPP: KeyPurposeId = {
  option: 'id_kp_eapOverPPP',
  value: '13'
};

export const id_kp_eapOverLAN: KeyPurposeId = {
  option: 'id_kp_eapOverLAN',
  value: '14'
};

export const id_kp_scvpServer: KeyPurposeId = {
  option: 'id_kp_scvpServer',
  value: '15'
};

export const id_kp_scvpClient: KeyPurposeId = {
  option: 'id_kp_scvpClient',
  value: '16'
};

export const id_kp_ipsecIKE: KeyPurposeId = {
  option: 'id_kp_ipsecIKE',
  value: '17'
};

export const id_kp_capwapAC: KeyPurposeId = {
  option: 'id_kp_capwapAC',
  value: '18'
};

export const id_kp_capwapWTP: KeyPurposeId = {
  option: 'id_kp_capwapWTP',
  value: '19'
};

export const keyPurposeIds = {
  id_kp_serverAuth,
  id_kp_clientAuth,
  id_kp_codeSigning,
  id_kp_emailProtection,
  id_kp_ipsecEndSystem,
  id_kp_ipsecTunnel,
  id_kp_ipsecUser,
  id_kp_timeStamping,
  id_kp_OCSPSigning,
  id_kp_dvcs,
  id_kp_sbgpCertAAServerAuth,
  id_kp_scvp_responder,
  id_kp_eapOverPPP,
  id_kp_eapOverLAN,
  id_kp_scvpServer,
  id_kp_scvpClient,
  id_kp_ipsecIKE,
  id_kp_capwapAC,
  id_kp_capwapWTP
};

export const getExtendedKeyUsages: (values: string[]) => KeyPurposeId[] = values => {
  return values.map(v => Object.values(keyPurposeIds)[parseInt(v, 10)]);
};

