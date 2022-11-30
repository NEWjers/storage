import {Item} from "./Item";

export class MovingRecordResponse {
  private readonly _count: number;
  private readonly _date: string;
  private readonly _type: string;
  private readonly _username: string;
  private readonly _item: Item;

  constructor(count: number, date: string, type: string, username: string, item: Item) {
    this._count = count;
    this._date = date;
    this._type = type;
    this._username = username;
    this._item = item;
  }


  get count(): number {
    return this._count;
  }

  get date(): string {
    return this._date;
  }

  get type(): string {
    return this._type;
  }

  get item(): Item {
    return this._item;
  }

  get username(): string {
    return this._username;
  }
}
