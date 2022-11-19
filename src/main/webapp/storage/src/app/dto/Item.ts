import { Producer } from "./Producer";

export class Item {
    id: number;
    code: string;
    size: string;
    pack: number;
    price: number;
    description: string;
    producer: Producer;


  constructor(id: number, code: string, size: string, pack: number, price: number, description: string, producer: Producer) {
    this.id = id;
    this.code = code;
    this.size = size;
    this.pack = pack;
    this.price = price;
    this.description = description;
    this.producer = producer;
  }
}
