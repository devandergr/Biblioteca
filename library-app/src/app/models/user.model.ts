import { LoanDTO } from './loan.model';
import { UserType } from './userType.model';

export interface User {
  id: number;
  firstName: string;
  lastName: string;
  username: string;
  password: string;
  userType: UserType;
  loans: LoanDTO[];
}
