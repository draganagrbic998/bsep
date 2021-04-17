import { Page } from '../models/page';

export const ADMIN = 'ADMIN';
export const DOCTOR = 'DOCTOR';
export const PAGE_SIZE = 10;
export const EMPTY_PAGE: Page<any> = {content: [], pageNumber: 0, first: true, last: true};
