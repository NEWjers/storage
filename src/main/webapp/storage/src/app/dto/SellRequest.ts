import {Item} from "./Item";

export class SellRequest {
  public itemCount: string;
  public item: Item;

  constructor(itemCount: string, item: Item) {
    this.itemCount = itemCount;
    this.item = item;
  }
}
