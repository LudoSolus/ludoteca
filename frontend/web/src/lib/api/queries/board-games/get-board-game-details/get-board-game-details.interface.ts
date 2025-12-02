import type { ECategory } from '$lib/shared/enums/category.enum';

export interface IGetBoardGameDetailsResponse {
	barcode: number;
	title: string;
	description: string;
	category: ECategory;
	minPlayers: number;
	maxPlayers: number;
	linkInstructionManual: string;
	linkVideoTutorial: string;
}
