import type { ECategory } from '$lib/shared/enums/category.enum';

export interface IBoardGame {
	id: string;
	barcode: string;
	name: string;
	category: ECategory;
	minParticipants: number;
	maxParticipants: number;
}
