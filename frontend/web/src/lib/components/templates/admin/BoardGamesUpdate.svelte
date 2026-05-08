<script lang="ts">
	import type { IEditBoardGameRequest } from '$lib/api/commands/board-games/edit-board-game/edit-board-game.interface';
	import type { IGetBoardGameDetailsResponse } from '$lib/api/queries/board-games/get-board-game-details/get-board-game-details.interface';
	import Button from '$lib/components/atoms/Button.svelte';
	import BoardGameForm from '$lib/components/molecules/forms/BoardGameForm.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	import { ECategory } from '$lib/shared/enums/category.enum';
	import { getEnumKeyByValue } from '$lib/shared/helpers/get-enum-key-by-value';
	import { toast } from 'svoast';

	export let boardGame: IGetBoardGameDetailsResponse;
	export let isLoading: boolean;
	export let onEditBoardGame: (data: IEditBoardGameRequest) => void;

	let formIsValid: boolean = false;
	let formValues: Record<keyof IEditBoardGameRequest, string> = {
		barcode: String(boardGame.barcode),
		title: boardGame.title,
		category: getEnumKeyByValue(ECategory, boardGame.category) ?? '',
		description: boardGame.description,
		minPlayers: String(boardGame.minPlayers),
		maxPlayers: String(boardGame.maxPlayers),
		linkInstructionManual: boardGame.linkInstructionManual,
		linkVideoTutorial: boardGame.linkVideoTutorial
	};

	function handleOnEdit() {
		if (!formIsValid) {
			toast.error('Preencha todos os campos devidamente.', { closable: true });
			return;
		}

		const formValuesFormated: IEditBoardGameRequest = {
			...formValues,
			barcode: Number(formValues.barcode.replace(/\D+/g, '')),
			minPlayers: Number(formValues.minPlayers.replace(/\D+/g, '')),
			maxPlayers: Number(formValues.maxPlayers.replace(/\D+/g, ''))
		};

		onEditBoardGame(formValuesFormated);
	}
</script>

<main
	class="flex w-full flex-col items-center gap-5 px-3 py-7 sm:px-10 md:gap-10 xl:px-15"
>
	<GoBack title="Editar Jogo" description="Edite os dados do jogo" />
	<BoardGameForm bind:isValid={formIsValid} {formValues} />
	<Button text="Salvar" width="290px" height="40px" onClick={handleOnEdit} loading={isLoading} />
</main>
