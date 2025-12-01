export interface IGetUserProfileDetailsResponse {
	publicId: string;
	name: string;
	cpf: string;
	institutionId?: string;
	phone: string;
	ra?: string;
	email: string;
	birthDate: Date;
}
