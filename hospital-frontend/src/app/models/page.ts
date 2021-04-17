export interface Page<T>{
    content: T[];
    pageNumber: number;
    first: boolean;
    last: boolean;
}
