export interface GetEventDetailsResponse {
	name: string;
	startDate: Date;
	finalDate: Date;
	street: string;
	number: number;
	supplement: string;
	neighborhood: string;
	city: string;
	state: string;
	zipCode: string;
	listGames: GameDetailsForEvent[];
}

interface GameDetailsForEvent {
	nameGame: string;
	isAvailable: boolean;
	category: string;
	minPlayers: number;
	maxPlayers: number;
}
