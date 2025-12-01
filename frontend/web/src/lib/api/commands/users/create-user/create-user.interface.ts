import type { EUserRole } from "$lib/shared/enums/user-role.enum";

export interface ICreateUserRequest {
	name: string,
  cpf: string,
  email: string,
  phone: string,
  ra: string | null,
  birthDate: Date,
  userRole: EUserRole,
  institutionId: string,
}
