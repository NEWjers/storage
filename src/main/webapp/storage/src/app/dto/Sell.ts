import {MovingRecord} from "./MovingRecord";

export class Sell {
  private readonly _id: number;
  private readonly _date: string;
  private readonly _items: MovingRecord[];

  constructor(id: number, date: string, items: MovingRecord[]) {
    this._id = id;
    this._date = date;
    this._items = items;
  }

  get id(): number {
    return this._id;
  }

  get date(): string {
    return this._date;
  }

  get items(): MovingRecord[] {
    return this._items;
  }
}
