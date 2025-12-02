import type { EUserRole } from "$lib/shared/enums/user-role.enum";

export interface IGetUserDetails {
	id: string;
	publicId: string;
	name: string;
	institution: string;
	phone: string;
	ra: string;
	email: string;
	ageCategory: string;
	userType: EUserRole;
	hoursBoardGames: Number;
	hoursEscapRoom: Number;
	hoursRpg: Number;
}
