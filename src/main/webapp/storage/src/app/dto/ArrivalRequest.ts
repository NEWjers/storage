import {Item} from "./Item";

export class ArrivalRequest {
  public itemCount: string;
  public username: string;
  public item: Item;


  constructor(itemCount: string, username: string, item: Item) {
    this.itemCount = itemCount;
    this.username = username;
    this.item = item;
  }
}
