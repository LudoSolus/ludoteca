import type { EUserRole } from "$lib/shared/enums/user-role.enum";

export interface IUser{
  id: string;
  email: string;
  ageCategory: string;
  roleUser: EUserRole;
}
