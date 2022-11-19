import {Item} from "./Item";

export class Record {
  id: number;
  count: number;
  item: Item;


  constructor(id: number, count: number, item: Item) {
    this.id = id;
    this.count = count;
    this.item = item;
  }
}
