import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'blodType'
})
export class BlodTypePipe implements PipeTransform {

  private convertToOperator(value: string): string{
    if (value === 'PLUS'){
      return '+';
    }
    return '-';
  }

  transform(value: string): string {
    return value.split('_')[0] + this.convertToOperator(value.split('_')[1]);
  }

}
