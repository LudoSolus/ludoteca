export interface IGetUserProfileDetailsResponse {
	publicId: string;
	name: string;
	cpf: string;
	instituitionId?: string;
	phone: string;
	ra?: string;
	email: string;
	birthDate: Date;
}
