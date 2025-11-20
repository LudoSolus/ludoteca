import type { ECategory } from "$lib/shared/enums/category.enum";

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

export interface GameDetailsForEvent {
	nameGame: string;
	isAvailable: boolean;
	category: ECategory;
	minPlayers: number;
	maxPlayers: number;
}
