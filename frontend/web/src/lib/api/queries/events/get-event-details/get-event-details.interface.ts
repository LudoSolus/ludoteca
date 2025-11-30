import type { ECategory } from '$lib/shared/enums/category.enum';
import type { EEventStatus } from '$lib/shared/enums/event-status.enum';

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
	status: EEventStatus;
	listGames: GameDetailsForEvent[];
}

export interface GameDetailsForEvent {
	id: string;
	barcode: string;
	nameGame: string;
	isAvailable: boolean;
	category: ECategory;
	minPlayers: number;
	maxPlayers: number;
}
