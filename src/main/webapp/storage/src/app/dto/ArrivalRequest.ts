import {Item} from "./Item";

export class ArrivalRequest {
  public itemCount: string;
  public item: Item;


  constructor(itemCount: string, item: Item) {
    this.itemCount = itemCount;
    this.item = item;
  }
}
