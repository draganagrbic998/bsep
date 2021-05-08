import { Page } from '../model/page';

export enum USER_ROLE {
  SUPER_ADMIN = 'SUPER_ADMIN',
  ADMIN = 'ADMIN',
  DOCTOR = 'DOCTOR'
}
export const ROOT_ALIAS = 'root';
export const EMPTY_PAGE: Page<any> = {content: [], totalElements: 0};
