import { Page } from '../models/page';

export enum USER_ROLE {
  SUPER_ADMIN = 'SUPER_ADMIN',
  ADMIN = 'ADMIN',
  DOCTOR = 'DOCTOR'
}
export const PAGE_SIZE = 10;
export const EMPTY_PAGE: Page<any> = {content: [], pageable: {pageNumber: 0}, first: true, last: true};
