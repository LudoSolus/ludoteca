import type { ECategory } from '../enums/category.enum';

export interface IBoardGameForList {
	id: string;
	barcode: string;
	name: string;
	category: ECategory;
	minParticipants: number;
	maxParticipants: number;
	isAvailable?: boolean;
}
