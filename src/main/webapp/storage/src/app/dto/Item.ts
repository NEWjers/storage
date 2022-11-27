import { Producer } from "./Producer";

export class Item {
    private readonly _id: number;
    private readonly _code: string;
    private readonly _size: string;
    private readonly _pack: number;
    private readonly _price: string;
    private readonly _description: string;
    private readonly _producer: Producer;


  constructor(id: number, code: string, size: string, pack: number, price: string, description: string, producer: Producer) {
    this._id = id;
    this._code = code;
    this._size = size;
    this._pack = pack;
    this._price = price;
    this._description = description;
    this._producer = producer;
  }


  get id(): number {
    return this._id;
  }

  get code(): string {
    return this._code;
  }

  get size(): string {
    return this._size;
  }

  get pack(): number {
    return this._pack;
  }

  get price(): string {
    return this._price;
  }

  get description(): string {
    return this._description;
  }

  get producer(): Producer {
    return this._producer;
  }
}
