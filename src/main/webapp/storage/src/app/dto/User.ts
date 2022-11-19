
export class User {
    private readonly _id: number;
    private readonly _username: string;
    private readonly _role: string;


  constructor(id: number, username: string, role: string) {
    this._id = id;
    this._username = username;
    this._role = role;
  }


  get id(): number {
    return this._id;
  }

  get username(): string {
    return this._username;
  }

  get role(): string {
    return this._role;
  }
}
