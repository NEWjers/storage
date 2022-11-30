
export class User {
    private readonly _id: number;
    private readonly _username: string;
    private readonly _role: string;
    private readonly _isArchived: boolean;


  constructor(id: number, username: string, role: string, isArchived: boolean) {
    this._id = id;
    this._username = username;
    this._role = role;
    this._isArchived = isArchived;
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

  get isArchived(): boolean {
    return this._isArchived;
  }
}
