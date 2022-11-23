export class Producer {
    private readonly _id: number;
    private readonly _name: string;
    private readonly _country: string;
    private readonly _description: string;


  constructor(id: number, name: string, country: string, description: string) {
    this._id = id;
    this._name = name;
    this._country = country;
    this._description = description;
  }


  get id(): number {
    return this._id;
  }

  get name(): string {
    return this._name;
  }

  get country(): string {
    return this._country;
  }

  get description(): string {
    return this._description;
  }
}
