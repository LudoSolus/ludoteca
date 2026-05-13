export interface IListNextEventsResponse {
	id: string;
	name: string;
	startDate: Date;
	finalDate: Date;
	street: string;
	number: string;
	neighborhood: string;
	city: string;
	state: string;
	zipCode: string;
	hasBoardGames: boolean;
	hasRpg: boolean;
	hasEscapeRoom: boolean;
}
