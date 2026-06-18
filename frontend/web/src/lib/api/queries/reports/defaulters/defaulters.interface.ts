export interface IDefaulterData {
	userId: string;
	userName: string;
	userEmail: string;
	gameTitle: string;
	expectedReturnDate: string;
	daysLate: number;
}

export type IDefaultersResponse = IDefaulterData[];
