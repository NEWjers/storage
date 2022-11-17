import { Producer } from "./Producer";

export interface Item {
    id: number;
    code: string;
    size: string;
    pack: number;
    price: number;
    description: string;
    producer: Producer;
}