export function dateFormat(date: Date): string{
    if (!date){
        return '';
    }
    return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
}
