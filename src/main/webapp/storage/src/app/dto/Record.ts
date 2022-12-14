import {Item} from "./Item";

export class Record {
  private readonly _id: number;
  private readonly _count: number;
  private readonly _item: Item;


  constructor(id: number, count: number, item: Item) {
    this._id = id;
    this._count = count;
    this._item = item;
  }


  get id(): number {
    return this._id;
  }

  get count(): number {
    return this._count;
  }

  get item(): Item {
    return this._item;
  }
}
