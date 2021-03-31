import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'blodType'
})
export class BlodTypePipe implements PipeTransform {

  transform(value: string): string {
    return value.split('_')[0] + (value.split('_')[1] === 'PLUS' ? '+' : '-');
  }

}
