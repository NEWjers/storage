import { Pipe, PipeTransform } from '@angular/core';
import {Item} from "../dto/Item";

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  transform(items: Item[], searchText: string): Item[] {
    if (!items) {
      return [];
    }

    if (!searchText) {
      return items;
    }

    searchText = searchText.toLocaleLowerCase();

    return items.filter(it => {
      return it.code.toLocaleLowerCase().includes(searchText);
    });
  }

}
