import type { ECategory } from "$lib/shared/enums/category.enum";

export interface IBoardGame {
	name: string;
	category: ECategory;
	minParticipants: number;
	maxParticipants: number;
}
